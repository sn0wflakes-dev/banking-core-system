package aji.intern.core.service;

import aji.intern.core.soap.dto.card.RegisterCardRequest;
import aji.intern.core.soap.dto.card.RegisterCardResponse;

public interface CardService {
    RegisterCardResponse registerCardService(RegisterCardRequest request);
}
