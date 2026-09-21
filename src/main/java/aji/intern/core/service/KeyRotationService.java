package aji.intern.core.service;

import aji.intern.core.rest.dto.security.*;

public interface KeyRotationService {
    RegisterServiceResponse registerService(RegisterServiceRequest request);
    RetrieveKeyResponse retrieveKey(RetrieveKeyRequest request);
    RotateKeyResponse rotateKey(RotateKeyRequest request);
}
