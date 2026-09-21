package aji.intern.core.service;

import aji.intern.core.soap.dto.card.*;

public interface CardService {
    RegisterCardResponse registerCardService(RegisterCardRequest request);
    ActivateCardResponse activateCardService(ActivateCardRequest request);
    AuthCardResponse authCardService(AuthCardRequest request);
}
