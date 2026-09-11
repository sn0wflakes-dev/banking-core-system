package aji.intern.core.service.impl;

import aji.intern.core.rest.dto.security.*;
import aji.intern.core.entity.KeyEntity;
import aji.intern.core.error.exception.key.ServiceIdAlreadyReserved;
import aji.intern.core.repository.KeyJpaRepository;
import aji.intern.core.security.RsaCrypto;
import aji.intern.core.service.KeyRotationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.KeyPair;

@Service
public class KeyRotationServiceImpl implements KeyRotationService {

    private static final Logger log = LogManager.getLogger(KeyRotationServiceImpl.class);

    private final KeyJpaRepository repository;

    public KeyRotationServiceImpl(KeyJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public RegisterServiceResponse registerService(RegisterServiceRequest request) {
        repository.findByServiceId(request.getRegisterServiceData().getServiceId()).ifPresent(
                key -> {
                    throw new ServiceIdAlreadyReserved(request.getRegisterServiceData().getServiceId());
                }
        );

        KeyEntity entity = new KeyEntity();
        KeyPair crypto = RsaCrypto.genKey();

        entity.setServiceId(request.getRegisterServiceData().getServiceId());
        entity.setPrivateKey(RsaCrypto.getPrivateKeyAsBase64(crypto.getPrivate()));
        entity.setPublicKey(RsaCrypto.getPublicKeyAsBase64(crypto.getPublic()));

        repository.save(entity);

        log.info("Success registering service with id {}", request.getRegisterServiceData().getServiceId());

        return toRegisterServiceRes(entity);

    }

    RegisterServiceResponse toRegisterServiceRes(KeyEntity entity) {
        return RegisterServiceResponse.builder()
                .serviceId(entity.getServiceId())
                .publicKey(entity.getPublicKey())
                .build();
    }

    @Override
    public RetrieveKeyResponse retrieveKey() {
        return null;
    }

    @Override
    public RotateKeyResponse rotateKey(RotateKeyRequest request) {
        return null;
    }
}
