package aji.intern.core.service.impl;

import aji.intern.core.soap.dto.ResponseHeader;
import aji.intern.core.soap.dto.customer.account.UpdateCustomerEmailRequest;
import aji.intern.core.soap.dto.customer.account.UpdateCustomerEmailResponse;
import aji.intern.core.entity.CustomerEntity;
import aji.intern.core.error.exception.customer.CifNotFoundException;
import aji.intern.core.repository.CustomerJpaRepository;
import aji.intern.core.service.CustomerAccountService;
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
    public UpdateCustomerEmailResponse updateCustomerEmail(UpdateCustomerEmailRequest request) {
        // find by gcif
        CustomerEntity customerEntity = repository.findByCif(request.getData().getGcif()).orElseThrow(
                () -> {
                    log.error("Failed to retrieve data : Customer with cif {} is not found",
                            request.getData().getGcif());
                    return new CifNotFoundException(request.getData().getGcif());
                }
        );

        if (customerEntity.getEmail().equalsIgnoreCase(request.getData().getEmail())) {
            log.info("Customer email is sync with updated version, cif={}", request.getData().getGcif());
            return toUpdateCustomerEmailRes(request.getHeader().getMessageId(), customerEntity);
        }

        // set email to entity
        customerEntity.setEmail(request.getData().getEmail());
        repository.save(customerEntity);
        log.info("Customer email updated successfully, cif={}", request.getData().getGcif());

        return toUpdateCustomerEmailRes(request.getHeader().getMessageId(), customerEntity);
    }

    private UpdateCustomerEmailResponse toUpdateCustomerEmailRes(String messageId, CustomerEntity entity)  {
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
