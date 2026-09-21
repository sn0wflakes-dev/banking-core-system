package aji.intern.core.service.impl;

import aji.intern.core.entity.CustomerEntity;
import aji.intern.core.entity.OtpEntity;
import aji.intern.core.error.exception.customer.CifNotFoundException;
import aji.intern.core.error.exception.otp.InvalidOtp;
import aji.intern.core.repository.CustomerJpaRepository;
import aji.intern.core.repository.OtpRedisRepository;
import aji.intern.core.rest.dto.email.Email;
import aji.intern.core.rest.dto.otp.GenerateOtpRequest;
import aji.intern.core.rest.dto.otp.GenerateOtpResponse;
import aji.intern.core.rest.dto.otp.VerifyOtpRequest;
import aji.intern.core.rest.dto.otp.VerifyOtpResponse;
import aji.intern.core.service.EmailService;
import aji.intern.core.service.OtpService;
import aji.intern.core.utils.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class OtpServiceImpl implements OtpService {

    private static final Logger log = LogManager.getLogger(OtpServiceImpl.class);

    private final OtpRedisRepository repository;
    private final CustomerJpaRepository customerRepository;
    private final EmailService emailService;

    private static final long OTP_EXPIRE_TIME = 300;

    public OtpServiceImpl(
            OtpRedisRepository repository, CustomerJpaRepository customerRepository, EmailService emailService) {
        this.repository = repository;
        this.customerRepository = customerRepository;
        this.emailService = emailService;
    }

    @Override
    public GenerateOtpResponse generateOtp(GenerateOtpRequest request) {
        CustomerEntity customerEntity = customerRepository
                .findByCif(request.getGenerateOtpData().getCif())
                .orElseThrow(() -> {
                    log.warn(
                            "Generate otp failed: customer with cif {} was not found",
                            StringUtil.maskString(request.getGenerateOtpData().getCif(), 18));
                    return new CifNotFoundException(request.getGenerateOtpData().getCif());
                });

        SecureRandom random = new SecureRandom();
        String otp = String.format("%06d", random.nextInt(999999));

        OtpEntity entity = new OtpEntity(customerEntity.getEmail() + otp, otp, OTP_EXPIRE_TIME);
        repository.save(entity);

        Email email = Email.builder()
                .customerName(customerEntity.getCustomerName())
                .email(customerEntity.getEmail())
                .otp(otp)
                .operation("OTP Verification")
                .expiry(String.valueOf(entity.getExpire() / 60))
                .build();

        emailService.sendEmail("OTP Verification", email);

        log.info(
                "Generate otp success: otp from {} successfully generated and sent to email",
                customerEntity.getEmail());

        return toGenerateOtpResponse(customerEntity.getEmail());
    }

    private GenerateOtpResponse toGenerateOtpResponse(String email) {
        return GenerateOtpResponse.builder()
                .email(email)
                .expire(String.valueOf(OtpServiceImpl.OTP_EXPIRE_TIME / 60))
                .build();
    }

    @Override
    public VerifyOtpResponse verifyOtp(VerifyOtpRequest request) {
        String id = request.getVerifyOtpData().getEmail()
                + request.getVerifyOtpData().getOtp();
        if (repository.existsById(id)) {
            repository.deleteById(id);
            log.info(
                    "Verification otp success: OTP from {} is valid",
                    request.getVerifyOtpData().getEmail());
            return toVerifyOtpResponse(request.getVerifyOtpData().getEmail());
        }

        log.warn(
                "Verification otp failed: OTP from {} is invalid",
                request.getVerifyOtpData().getEmail());
        throw new InvalidOtp();
    }

    private VerifyOtpResponse toVerifyOtpResponse(String email) {
        return VerifyOtpResponse.builder().email(email).status("valid").build();
    }
}
