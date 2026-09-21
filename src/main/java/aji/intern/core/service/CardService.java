package aji.intern.core.service;

import aji.intern.core.soap.dto.card.*;

public interface CardService {
    RegisterCardResponse registerCard(RegisterCardRequest request);
    ActivateCardResponse activateCard(ActivateCardRequest request);
    AuthCardResponse authCard(AuthCardRequest request);
}
