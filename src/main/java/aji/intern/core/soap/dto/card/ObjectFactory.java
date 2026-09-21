package aji.intern.core.soap.dto.card;

import aji.intern.core.soap.controller.WebserviceEndpoint;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;

import javax.xml.namespace.QName;

public class ObjectFactory {
    private interface QNames {
        QName RegisterCardResponse =
                new QName(WebserviceEndpoint.NAMESPACE_CARD_SERVICE, "RegisterCardResponse");
        QName ActivateCardResponse =
                new QName(WebserviceEndpoint.NAMESPACE_CARD_SERVICE, "ActivateCardResponse");
        QName AuthCardResponse =
                new QName(WebserviceEndpoint.NAMESPACE_CARD_SERVICE, "AuthCardResponse");
    }

    @XmlElementDecl(namespace = WebserviceEndpoint.NAMESPACE_CARD_SERVICE, name = "RegisterCardRequest")
    public JAXBElement<RegisterCardResponse> createRegisterCardResponse(RegisterCardResponse value) {
        return new JAXBElement<>(QNames.RegisterCardResponse, RegisterCardResponse.class, null, value);
    }

    @XmlElementDecl(namespace = WebserviceEndpoint.NAMESPACE_CARD_SERVICE, name = "ActivateCardResponse")
    public JAXBElement<ActivateCardResponse> createActivateCardResponse(ActivateCardResponse value) {
        return new JAXBElement<>(QNames.ActivateCardResponse, ActivateCardResponse.class, null, value);
    }

    @XmlElementDecl(namespace = WebserviceEndpoint.NAMESPACE_CARD_SERVICE, name = "AuthCardResponse")
    public JAXBElement<AuthCardResponse> createAuthCardResponse(AuthCardResponse value) {
        return new JAXBElement<>(QNames.AuthCardResponse, AuthCardResponse.class, null, value);
    }
}
