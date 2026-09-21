package aji.intern.core.service.impl;

import aji.intern.core.error.exception.customer.EmailAlreadyExists;
import aji.intern.core.error.exception.customer.PhoneNumberAlreadyExist;
import aji.intern.core.soap.dto.ResponseHeader;
import aji.intern.core.soap.dto.account.*;
import aji.intern.core.entity.CustomerEntity;
import aji.intern.core.error.exception.customer.CifNotFoundException;
import aji.intern.core.repository.CustomerJpaRepository;
import aji.intern.core.service.CustomerAccountService;
import aji.intern.core.utils.DateUtil;
import aji.intern.core.utils.StringUtil;
import com.github.f4b6a3.uuid.UuidCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class CustomerAccountServiceImpl implements CustomerAccountService {

    private static final Logger log = LogManager.getLogger(CustomerAccountServiceImpl.class);

    private final CustomerJpaRepository repository;

    public CustomerAccountServiceImpl(CustomerJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public RegisterCustomerAccountResponse registerCustomerAccount(RegisterCustomerAccountRequest request) {
        // Check existing phone number
        repository.findByMobileNumber(request.getData().getPhoneNumber()).ifPresent(data -> {
            log.warn(
                    "Customer registration failed: phone number {} is already exist",
                    request.getData().getPhoneNumber());
            throw new PhoneNumberAlreadyExist();
        });

        // Check existing email address
        repository.findByEmail(request.getData().getEmail()).ifPresent(data -> {
            log.warn(
                    "Customer registration failed: email address {} is already exist",
                    request.getData().getEmail());
            throw new EmailAlreadyExists();
        });

        CustomerEntity entity = CustomerEntity.builder()
                .customerNumber(UuidCreator.getTimeOrderedEpoch().toString())
                .cif(UuidCreator.getTimeOrderedEpoch().toString())
                .customerName(request.getData().getName())
                .birthDate(DateUtil.stringToDate(request.getData().getBirthDate()))
                .mobileNumber(request.getData().getPhoneNumber())
                .email(request.getData().getEmail())
                .address(request.getData().getAddress())
                .build();

        repository.save(entity);

        log.info(
                "Card registration success: customer with number {} successfully registered",
                StringUtil.maskString(entity.getCustomerNumber(), 18));

        return toRegisterCustomerAccountResponse(request.getHeader().getMessageId(), entity);
    }

    private RegisterCustomerAccountResponse toRegisterCustomerAccountResponse(String messageId, CustomerEntity entity) {
        return RegisterCustomerAccountResponse.builder()
                .header(ResponseHeader.builder()
                        .messageId(messageId)
                        .responseCode("00")
                        .errorOrigin(null)
                        .responseMessage("account created")
                        .build())
                .data(RegisterCustomerAccountResponse.RegisterCustomerAccountData.builder()
                        .customerNumber(entity.getCustomerNumber())
                        .name(entity.getCustomerName())
                        .birthDate(DateUtil.dateToString(entity.getBirthDate()))
                        .phoneNumber(entity.getMobileNumber())
                        .email(entity.getEmail())
                        .address(entity.getAddress())
                        .cif(entity.getCif())
                        .build())
                .build();
    }

    @Override
    public UpdateCustomerEmailResponse updateCustomerEmail(UpdateCustomerEmailRequest request) {
        // find by cif
        CustomerEntity customerEntity = repository
                .findByCif(request.getData().getCif())
                .orElseThrow(() -> {
                    log.warn(
                            "Update email failed: customer with cif {} was not found",
                            StringUtil.maskString(request.getData().getCif(), 18));
                    return new CifNotFoundException(request.getData().getCif());
                });

        repository.findByEmail(request.getData().getEmail()).ifPresent(data -> {
            log.warn(
                    "Update email failed: email {} is already exist",
                    request.getData().getEmail());
            throw new EmailAlreadyExists();
        });

        if (customerEntity.getEmail().equalsIgnoreCase(request.getData().getEmail())) {
            log.info(
                    "Customer email is sync with updated version, cif={}",
                    StringUtil.maskString(request.getData().getCif(), 18));
            return toUpdateCustomerEmailRes(request.getHeader().getMessageId(), customerEntity);
        }

        // set email to entity
        customerEntity.setEmail(request.getData().getEmail());
        repository.save(customerEntity);

        log.info(
                "Update email success: customer with cif {} successfully updated",
                StringUtil.maskString(request.getData().getCif(), 18));

        return toUpdateCustomerEmailRes(request.getHeader().getMessageId(), customerEntity);
    }

    private UpdateCustomerEmailResponse toUpdateCustomerEmailRes(String messageId, CustomerEntity entity) {
        return UpdateCustomerEmailResponse.builder()
                .header(ResponseHeader.builder()
                        .messageId(messageId)
                        .responseCode("00")
                        .errorOrigin(null)
                        .responseMessage("email updated")
                        .build())
                .data(UpdateCustomerEmailResponse.UpdateCustomerEmailData.builder()
                        .cif(entity.getCif())
                        .updatedEmail(entity.getEmail())
                        .build())
                .build();
    }

    @Override
    public UpdatePhoneNumberResponse updatePhoneNumber(UpdatePhoneNumberRequest request) {
        CustomerEntity customerEntity = repository
                .findByCif(request.getData().getCif())
                .orElseThrow(() -> {
                    log.warn(
                            "Update phone number failed: customer with cif {} was not found",
                            StringUtil.maskString(request.getData().getCif(), 18));
                    return new CifNotFoundException(request.getData().getCif());
                });

        repository.findByMobileNumber(request.getData().getPhoneNumber()).ifPresent(
                data -> {
                    log.warn(
                            "Update phone number failed: phone number {} is already exist",
                            request.getData().getPhoneNumber());
                    throw new PhoneNumberAlreadyExist();
                }
        );

        if (customerEntity.getMobileNumber().equalsIgnoreCase(request.getData().getPhoneNumber())) {
            log.info(
                    "Customer phone number is sync with updated version, cif={}",
                    StringUtil.maskString(request.getData().getCif(), 18));
            return toUpdatePhoneNumberResponse(request.getHeader().getMessageId(), customerEntity);
        }

        customerEntity.setMobileNumber(request.getData().getPhoneNumber());
        repository.save(customerEntity);

        log.info(
                "Update phone number success: customer with cif {} successfully updated",
                StringUtil.maskString(request.getData().getCif(), 18));

        return toUpdatePhoneNumberResponse(request.getHeader().getMessageId(), customerEntity);
    }

    private UpdatePhoneNumberResponse toUpdatePhoneNumberResponse(String messageId, CustomerEntity entity) {
        return UpdatePhoneNumberResponse.builder()
                .header(ResponseHeader.builder()
                        .messageId(messageId)
                        .responseCode("00")
                        .errorOrigin(null)
                        .responseMessage("Phone number updated successfully")
                        .build())
                .data(UpdatePhoneNumberResponse.UpdatePhoneNumberData.builder()
                        .cif(entity.getCif())
                        .updatedPhoneNumber(entity.getMobileNumber())
                        .build())
                .build();
    }

}
