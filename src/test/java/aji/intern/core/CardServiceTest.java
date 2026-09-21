package aji.intern.core;

import aji.intern.core.entity.CardEntity;
import aji.intern.core.entity.CustomerEntity;
import aji.intern.core.entity.KeyEntity;
import aji.intern.core.repository.CardJpaRepository;
import aji.intern.core.repository.CustomerJpaRepository;
import aji.intern.core.repository.KeyJpaRepository;
import aji.intern.core.security.RsaCrypto;
import aji.intern.core.service.impl.CardServiceImpl;
import aji.intern.core.soap.dto.RequestHeader;
import aji.intern.core.soap.dto.card.RegisterCardRequest;
import aji.intern.core.soap.dto.card.RegisterCardResponse;
import aji.intern.core.utils.DateUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.KeyPair;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CardServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CardJpaRepository cardJpaRepository;

    @Mock
    private CustomerJpaRepository customerJpaRepository;

    @Mock
    private KeyJpaRepository keyJpaRepository;

    @InjectMocks
    private CardServiceImpl service;

    private final String customerNumber = UUID.randomUUID().toString();
    private final String cif = UUID.randomUUID().toString();
    private final String customerName = "John Doe";
    private final String birthDate = "12-04-2002";
    private final String phoneNumber = "0877127201";
    private final String email = "john@gmail.com";
    private final String address = "Kebagusan, Kota Jakarta Pusat";

    private final String pin = "123456";

    private final String serviceId = UUID.randomUUID().toString();

    @Test
    void registerCardOnSuccess() throws Exception {
        // Arrange
        CustomerEntity customerEntity = CustomerEntity.builder()
                .customerNumber(customerNumber)
                .cif(cif)
                .customerName(customerName)
                .birthDate(DateUtil.stringToDate(birthDate))
                .mobileNumber(phoneNumber)
                .email(email)
                .address(address)
                .build();

        KeyPair keyPair = RsaCrypto.genKey();
        KeyEntity key = KeyEntity.builder()
                .serviceId(serviceId)
                .privateKey(RsaCrypto.getPrivateKeyAsBase64(keyPair.getPrivate()))
                .publicKey(RsaCrypto.getPublicKeyAsBase64(keyPair.getPublic()))
                .registeredAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        RegisterCardRequest request = RegisterCardRequest.builder()
                .header(RequestHeader.builder()
                        .messageId(UUID.randomUUID().toString())
                        .serviceId(serviceId)
                        .channelId("IVR")
                        .sequenceNumber("0101230")
                        .transactionDate("02:10:2023")
                        .transactionTime("02:20:39")
                        .build())
                .data(RegisterCardRequest.RegisterCardData.builder()
                        .pin(RsaCrypto.encrypt(pin, key.getPublicKey()))
                        .customerNumber(customerNumber)
                        .build())
                .build();
        // Mock
        when(customerJpaRepository.findById(customerNumber)).thenReturn(Optional.of(customerEntity));
        when(keyJpaRepository.findByServiceId(serviceId)).thenReturn(Optional.of(key));
        when(cardJpaRepository.save(any(CardEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0, CardEntity.class));

        // Act
        RegisterCardResponse result = service.registerCardService(request);

        // Assert
        assertNotNull(result);

        assertEquals(customerNumber, result.getData().getCustomerNumber());
        assertNotNull(result.getData().getCardNumber());
    }
}
