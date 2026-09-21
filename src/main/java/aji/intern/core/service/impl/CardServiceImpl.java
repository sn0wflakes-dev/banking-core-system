package aji.intern.core.service.impl;

import aji.intern.core.entity.CardEntity;
import aji.intern.core.entity.CustomerEntity;
import aji.intern.core.entity.KeyEntity;
import aji.intern.core.error.exception.card.CardNumberNotFound;
import aji.intern.core.error.exception.card.InvalidCredential;
import aji.intern.core.error.exception.customer.CustomerNumberNotFound;
import aji.intern.core.error.exception.key.ServiceIdNotFound;
import aji.intern.core.helper.CardNumberBuilder;
import aji.intern.core.repository.CardJpaRepository;
import aji.intern.core.repository.CustomerJpaRepository;
import aji.intern.core.repository.KeyJpaRepository;
import aji.intern.core.security.RsaCrypto;
import aji.intern.core.service.CardService;
import aji.intern.core.soap.dto.ResponseHeader;
import aji.intern.core.soap.dto.card.*;
import aji.intern.core.utils.DateUtil;
import aji.intern.core.utils.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class CardServiceImpl implements CardService {

    private static final Logger log = LogManager.getLogger(CardServiceImpl.class);

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
    public RegisterCardResponse registerCard(RegisterCardRequest request) {
        // Check customer account first
        CustomerEntity customerEntity = customerRepository
                .findById(request.getData().getCustomerNumber())
                .orElseThrow(() -> {
                    log.warn(
                            "Card registration failed: customer with number {} was not found",
                            request.getData().getCustomerNumber());
                    return new CustomerNumberNotFound(request.getData().getCustomerNumber());
                });

        // decrypt and hash the password
        KeyEntity key = keyJpaRepository
                .findByServiceId(request.getHeader().getServiceId())
                .orElseThrow(() -> {
                    log.warn(
                            "Card registration failed: service with id {} was not found",
                            request.getHeader().getServiceId());
                    return new ServiceIdNotFound(request.getHeader().getServiceId());
                });

        String pin = passwordEncoder.encode(RsaCrypto.decrypt(request.getData().getPin(), key.getPrivateKey()));

        // init and store all information
        String pan = new CardNumberBuilder()
                .setBIN("411111")
                .generateRandomAccountIdentifier()
                .build();
        CardEntity cardEntity = CardEntity.builder()
                .pan(pan)
                .pin(pin)
                .cardStatus("inactive")
                .expiryDate(LocalDate.now().plusYears(5))
                .customerEntity(customerEntity)
                .build();

        repository.save(cardEntity);

        log.info(
                "Card registration success: card with number {} successfully registered",
                StringUtil.maskString(pan, 8));

        return toRegisterCardResponse(request.getHeader().getMessageId(), cardEntity);
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

    @Override
    public ActivateCardResponse activateCard(ActivateCardRequest request) {
        CardEntity cardEntity = repository
                .findByPan(request.getData().getCardNumber())
                .orElseThrow(() -> {
                    log.warn(
                            "Card activation failed: card with number {} was not found",
                            StringUtil.maskString(request.getData().getCardNumber(), 8));
                    return new CardNumberNotFound(request.getData().getCardNumber());
                });

        cardEntity.setCardStatus("active");
        repository.save(cardEntity);

        log.info(
                "Card activation success: card with number {} successfully activated",
                StringUtil.maskString(request.getData().getCardNumber(), 8));

        return toActivateCardResponse(request.getHeader().getMessageId(), cardEntity);
    }

    private ActivateCardResponse toActivateCardResponse(String messageId, CardEntity entity) {
        return ActivateCardResponse.builder()
                .header(ResponseHeader.builder()
                        .responseCode("00")
                        .messageId(messageId)
                        .responseMessage("Success activated card")
                        .build())
                .data(ActivateCardResponse.ActivateCardData.builder()
                        .cardNumber(entity.getPan())
                        .status(entity.getCardStatus())
                        .build())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public AuthCardResponse authCard(AuthCardRequest request) {
        // check card number
        CardEntity cardEntity = repository
                .findByPan(request.getData().getCardNumber())
                .orElseThrow(() -> {
                    log.warn(
                            "Card authentication failed: card with number {} was not found",
                            StringUtil.maskString(request.getData().getCardNumber(), 8));
                    return new InvalidCredential();
                });

        // decrypt and hash the password
        KeyEntity key = keyJpaRepository
                .findByServiceId(request.getHeader().getServiceId())
                .orElseThrow(() -> {
                    log.warn(
                            "Card authentication failed: service with id {} was not found",
                            request.getHeader().getServiceId());
                    return new ServiceIdNotFound(request.getHeader().getServiceId());
                });

        String pin = RsaCrypto.decrypt(request.getData().getPin(), key.getPrivateKey());

        if (!passwordEncoder.matches(pin, cardEntity.getPin())) {
            log.warn(
                    "Card authentication failed: PIN is invalid with card number : {}",
                    StringUtil.maskString(request.getData().getCardNumber(), 8));
            throw new InvalidCredential();
        }

        CardEntity entity = repository
                .findByPanWithCustomer(request.getData().getCardNumber())
                .orElseThrow(InvalidCredential::new);

        log.info(
                "Card authentication success: card with number {} successfully authenticated",
                StringUtil.maskString(request.getData().getCardNumber(), 8));

        return toAuthCardResponse(request.getHeader().getMessageId(), entity);
    }

    private AuthCardResponse toAuthCardResponse(String messageId, CardEntity entity) {
        return AuthCardResponse.builder()
                .header(ResponseHeader.builder()
                        .responseCode("00")
                        .messageId(messageId)
                        .responseMessage("Success authenticated card")
                        .build())
                .data(AuthCardResponse.AuthCardData.builder()
                        .customerNumber(entity.getCustomerEntity().getCustomerNumber())
                        .name(entity.getCustomerEntity().getCustomerName())
                        .address(entity.getCustomerEntity().getAddress())
                        .cif(entity.getCustomerEntity().getCif())
                        .cardNumber(entity.getPan())
                        .build())
                .build();
    }
}
