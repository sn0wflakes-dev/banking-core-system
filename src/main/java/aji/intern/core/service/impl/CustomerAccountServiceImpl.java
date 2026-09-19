package aji.intern.core.service.impl;

import aji.intern.core.error.exception.customer.EmailAlreadyExists;
import aji.intern.core.error.exception.customer.PhoneNumberAlreadyExist;
import aji.intern.core.soap.dto.ResponseHeader;
import aji.intern.core.soap.dto.customer.account.RegisterCustomerAccountRequest;
import aji.intern.core.soap.dto.customer.account.RegisterCustomerAccountResponse;
import aji.intern.core.soap.dto.customer.account.UpdateCustomerEmailRequest;
import aji.intern.core.soap.dto.customer.account.UpdateCustomerEmailResponse;
import aji.intern.core.entity.CustomerEntity;
import aji.intern.core.error.exception.customer.CifNotFoundException;
import aji.intern.core.repository.CustomerJpaRepository;
import aji.intern.core.service.CustomerAccountService;
import aji.intern.core.utils.DateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
            throw new PhoneNumberAlreadyExist();
        });

        // Check existing email address
        repository.findByEmail(request.getData().getEmail()).ifPresent(data -> {
            throw new EmailAlreadyExists();
        });

        CustomerEntity entity = CustomerEntity.builder()
                .customerNumber(UUID.randomUUID().toString())
                .cif(UUID.randomUUID().toString())
                .customerName(request.getData().getName())
                .birthDate(DateUtil.stringToDate(request.getData().getBirthDate()))
                .mobileNumber(request.getData().getPhoneNumber())
                .email(request.getData().getEmail())
                .address(request.getData().getAddress())
                .build();

        repository.save(entity);
        log.info(
                "Successfully created account for {}", request.getData().getName());

        return toRegisterCustomerAccountResponse(request.getHeader().getMessageId(), entity);
    }

    private RegisterCustomerAccountResponse toRegisterCustomerAccountResponse(
            String messageId,
            CustomerEntity entity) {
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
        // find by gcif
        CustomerEntity customerEntity = repository
                .findByCif(request.getData().getGcif())
                .orElseThrow(() -> {
                    log.error(
                            "Failed to retrieve data : Customer with cif {} is not found",
                            request.getData().getGcif());
                    return new CifNotFoundException(request.getData().getGcif());
                });

        if (customerEntity.getEmail().equalsIgnoreCase(request.getData().getEmail())) {
            log.info(
                    "Customer email is sync with updated version, cif={}",
                    request.getData().getGcif());
            return toUpdateCustomerEmailRes(request.getHeader().getMessageId(), customerEntity);
        }

        // set email to entity
        customerEntity.setEmail(request.getData().getEmail());
        repository.save(customerEntity);
        log.info(
                "Customer email updated successfully, cif={}", request.getData().getGcif());

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
                        .gcif(entity.getCif())
                        .updatedEmail(entity.getEmail())
                        .build())
                .build();
    }
}
