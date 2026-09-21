package aji.intern.core.soap.dto.account;

import aji.intern.core.soap.controller.WebserviceEndpoint;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;

import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {
    private interface QNames {
        QName UpdateCustomerEmailRequest =
                new QName(WebserviceEndpoint.NAMESPACE_ACCOUNT_SERVICE, "UpdateCustomerEmailRequest");
        QName UpdateCustomerEmailResponse =
                new QName(WebserviceEndpoint.NAMESPACE_ACCOUNT_SERVICE, "UpdateCustomerEmailResponse");

        QName RegisterCustomerAccountRequest =
                new QName(WebserviceEndpoint.NAMESPACE_ACCOUNT_SERVICE, "RegisterCustomerAccountRequest");
        QName RegisterCustomerAccountResponse =
                new QName(WebserviceEndpoint.NAMESPACE_ACCOUNT_SERVICE, "RegisterCustomerAccountResponse");
    }

    @XmlElementDecl(namespace = WebserviceEndpoint.NAMESPACE_ACCOUNT_SERVICE, name = "UpdateCustomerEmailRequest")
    public JAXBElement<UpdateCustomerEmailRequest> createUpdateCustomerEmailRequest(UpdateCustomerEmailRequest value) {
        return new JAXBElement<>(QNames.UpdateCustomerEmailRequest, UpdateCustomerEmailRequest.class, null, value);
    }

    @XmlElementDecl(namespace = WebserviceEndpoint.NAMESPACE_ACCOUNT_SERVICE, name = "UpdateCustomerEmailResponse")
    public JAXBElement<UpdateCustomerEmailResponse> createUpdateCustomerEmailResponse(
            UpdateCustomerEmailResponse value) {
        return new JAXBElement<>(QNames.UpdateCustomerEmailResponse, UpdateCustomerEmailResponse.class, null, value);
    }

    @XmlElementDecl(namespace = WebserviceEndpoint.NAMESPACE_ACCOUNT_SERVICE, name = "RegisterCustomerAccountRequest")
    public JAXBElement<RegisterCustomerAccountRequest> createRegisterCustomerAccountRequest(
            RegisterCustomerAccountRequest value) {
        return new JAXBElement<>(QNames.RegisterCustomerAccountRequest, RegisterCustomerAccountRequest.class, null, value);
    }

    @XmlElementDecl(namespace = WebserviceEndpoint.NAMESPACE_ACCOUNT_SERVICE, name = "RegisterCustomerAccountResponse")
    public JAXBElement<RegisterCustomerAccountResponse> createRegisterCustomerAccountResponse(
            RegisterCustomerAccountResponse value) {
        return new JAXBElement<>(
                QNames.RegisterCustomerAccountResponse, RegisterCustomerAccountResponse.class, null, value);
    }
}
