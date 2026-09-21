package aji.intern.core.service.impl;

import aji.intern.core.entity.CardEntity;
import aji.intern.core.entity.CustomerEntity;
import aji.intern.core.entity.KeyEntity;
import aji.intern.core.error.exception.customer.CustomerNumberNotFound;
import aji.intern.core.error.exception.key.ServiceIdNotFound;
import aji.intern.core.helper.CardNumberBuilder;
import aji.intern.core.repository.CardJpaRepository;
import aji.intern.core.repository.CustomerJpaRepository;
import aji.intern.core.repository.KeyJpaRepository;
import aji.intern.core.security.RsaCrypto;
import aji.intern.core.service.CardService;
import aji.intern.core.soap.dto.ResponseHeader;
import aji.intern.core.soap.dto.card.RegisterCardRequest;
import aji.intern.core.soap.dto.card.RegisterCardResponse;
import aji.intern.core.utils.DateUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CardServiceImpl implements CardService {

    private final CardJpaRepository repository;
    private final CustomerJpaRepository customerRepository;
    private final KeyJpaRepository keyJpaRepository;
    private final PasswordEncoder passwordEncoder;

    public CardServiceImpl(
            CardJpaRepository repository,
            CustomerJpaRepository customerRepository,
            KeyJpaRepository keyJpaRepository,
            PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.customerRepository = customerRepository;
        this.keyJpaRepository = keyJpaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegisterCardResponse registerCardService(RegisterCardRequest request) {
        try {
            // Check customer account first
            CustomerEntity customerEntity = customerRepository
                    .findById(request.getData().getCustomerNumber())
                    .orElseThrow(
                            () -> new CustomerNumberNotFound(request.getData().getCustomerNumber()));

            // decrypt and hash the password
            KeyEntity key = keyJpaRepository
                    .findByServiceId(request.getHeader().getServiceId())
                    .orElseThrow(() -> new ServiceIdNotFound(request.getHeader().getServiceId()));

            String pin = passwordEncoder
                    .encode(RsaCrypto.decrypt(request.getData().getPin(), key.getPrivateKey()));

            // init and store all information
            String pan = new CardNumberBuilder().setBIN("411111").generateRandomAccountIdentifier().build();
            CardEntity cardEntity = CardEntity.builder()
                    .pan(pan)
                    .pin(pin)
                    .cardStatus("inactive")
                    .expiryDate(LocalDate.now().plusYears(5))
                    .customerEntity(customerEntity)
                    .build();

            repository.save(cardEntity);

            return toRegisterCardResponse(request.getHeader().getMessageId(), cardEntity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private RegisterCardResponse toRegisterCardResponse(String messageId, CardEntity entity) {
        return RegisterCardResponse.builder()
                .header(ResponseHeader.builder()
                        .responseCode("00")
                        .messageId(messageId)
                        .responseMessage("Success registered card")
                        .build())
                .data(RegisterCardResponse.RegisterCardData.builder()
                        .customerNumber(entity.getCustomerEntity().getCustomerNumber())
                        .cif(entity.getCustomerEntity().getCif())
                        .cardNumber(entity.getPan())
                        .expiryDate(DateUtil.expDateToString(entity.getExpiryDate()))
                        .status(entity.getCardStatus())
                        .build())
                .build();
    }
}
