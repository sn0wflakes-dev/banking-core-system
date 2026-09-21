package aji.intern.core.soap.controller;

import aji.intern.core.service.CardService;
import aji.intern.core.soap.dto.card.ObjectFactory;
import aji.intern.core.soap.dto.card.RegisterCardRequest;
import aji.intern.core.soap.dto.card.RegisterCardResponse;
import jakarta.xml.bind.JAXBElement;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class CardController {
    private final CardService service;
    private final ObjectFactory objectFactory;

    public CardController(CardService service) {
        this.service = service;
        this.objectFactory = new ObjectFactory();
    }

    @PayloadRoot(namespace = WebserviceEndpoint.NAMESPACE_CARD_SERVICE, localPart = "RegisterCardRequest")
    @ResponsePayload
    public JAXBElement<RegisterCardResponse> registerCardEndpoint(
            @RequestPayload JAXBElement<RegisterCardRequest> request) {
        RegisterCardResponse response = service.registerCardService(request.getValue());
        return objectFactory.createRegisterCardResponse(response);
    }
}
