package aji.intern.core.service.impl;

import aji.intern.core.error.exception.key.ServiceIdNotFound;
import aji.intern.core.rest.dto.security.*;
import aji.intern.core.entity.KeyEntity;
import aji.intern.core.error.exception.key.ServiceIdAlreadyReserved;
import aji.intern.core.repository.KeyJpaRepository;
import aji.intern.core.security.RsaCrypto;
import aji.intern.core.service.KeyRotationService;
import aji.intern.core.utils.StringUtil;
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
        repository
                .findByServiceId(request.getRegisterServiceData().getServiceId())
                .ifPresent(key -> {
                    log.warn(
                            "Service registration failed: service id {} is already exist",
                            request.getRegisterServiceData().getServiceId());
                    throw new ServiceIdAlreadyReserved(
                            request.getRegisterServiceData().getServiceId());
                });

        KeyEntity entity = new KeyEntity();
        KeyPair crypto = RsaCrypto.genKey();

        entity.setServiceId(request.getRegisterServiceData().getServiceId());
        entity.setPrivateKey(RsaCrypto.getPrivateKeyAsBase64(crypto.getPrivate()));
        entity.setPublicKey(RsaCrypto.getPublicKeyAsBase64(crypto.getPublic()));

        repository.save(entity);

        log.info(
                "Service registration success: service with id {} successfully registered",
                request.getRegisterServiceData().getServiceId());

        return toRegisterServiceRes(entity);
    }

    RegisterServiceResponse toRegisterServiceRes(KeyEntity entity) {
        return RegisterServiceResponse.builder()
                .serviceId(entity.getServiceId())
                .publicKey(entity.getPublicKey())
                .build();
    }

    @Override
    public RetrieveKeyResponse retrieveKey(RetrieveKeyRequest request) {
        KeyEntity entity = repository
                .findByServiceId(request.getServiceId())
                .orElseThrow(() -> {
                    log.warn(
                            "Retrieve service id failed: service with id {} was not found",
                            request.getServiceId());
                    return new ServiceIdNotFound(request.getServiceId());
                });

        log.info(
                "Retrieve service id success: service id {} successfully retrieved",
                request.getServiceId());

        return toRetrieveKeyResponse(entity);
    }

    RetrieveKeyResponse toRetrieveKeyResponse(KeyEntity entity) {
        return RetrieveKeyResponse.builder().key(entity.getPublicKey()).build();
    }

    @Override
    public RotateKeyResponse rotateKey(RotateKeyRequest request) {
        KeyEntity entity = repository
                .findByServiceId(request.getRotateKeyData().getServiceId())
                .orElseThrow(
                        () -> {
                            log.warn(
                                    "Rotate key failed: service with id {} was not found",
                                    request.getRotateKeyData().getServiceId());
                            return new ServiceIdNotFound(request.getRotateKeyData().getServiceId());
                        });

        KeyPair crypto = RsaCrypto.genKey();
        entity.setPrivateKey(RsaCrypto.getPrivateKeyAsBase64(crypto.getPrivate()));
        entity.setPublicKey(RsaCrypto.getPublicKeyAsBase64(crypto.getPublic()));

        repository.save(entity);
        
        log.info(
                "Rotate key success: key with service id {} successfully rotated",
                request.getRotateKeyData().getServiceId());

        return toRotateKeyResponse(entity);
    }

    RotateKeyResponse toRotateKeyResponse(KeyEntity entity) {
        return RotateKeyResponse.builder()
                .serviceId(entity.getServiceId())
                .generatedKey(entity.getPublicKey())
                .build();
    }
}
