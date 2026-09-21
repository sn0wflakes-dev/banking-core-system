package aji.intern.core.soap.dto.account;

import aji.intern.core.soap.controller.WebserviceEndpoint;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;

import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {
    private interface QNames {
        QName UpdateCustomerEmailResponse =
                new QName(WebserviceEndpoint.NAMESPACE_ACCOUNT_SERVICE, "UpdateCustomerEmailResponse");

        QName RegisterCustomerAccountResponse =
                new QName(WebserviceEndpoint.NAMESPACE_ACCOUNT_SERVICE, "RegisterCustomerAccountResponse");

        QName UpdatePhoneNumberResponse =
                new QName(WebserviceEndpoint.NAMESPACE_ACCOUNT_SERVICE, "UpdatePhoneNumberResponse");
    }

    @XmlElementDecl(namespace = WebserviceEndpoint.NAMESPACE_ACCOUNT_SERVICE, name = "UpdateCustomerEmailResponse")
    public JAXBElement<UpdateCustomerEmailResponse> createUpdateCustomerEmailResponse(
            UpdateCustomerEmailResponse value) {
        return new JAXBElement<>(QNames.UpdateCustomerEmailResponse, UpdateCustomerEmailResponse.class, null, value);
    }

    @XmlElementDecl(namespace = WebserviceEndpoint.NAMESPACE_ACCOUNT_SERVICE, name = "RegisterCustomerAccountResponse")
    public JAXBElement<RegisterCustomerAccountResponse> createRegisterCustomerAccountResponse(
            RegisterCustomerAccountResponse value) {
        return new JAXBElement<>(
                QNames.RegisterCustomerAccountResponse, RegisterCustomerAccountResponse.class, null, value);
    }

    @XmlElementDecl(namespace = WebserviceEndpoint.NAMESPACE_ACCOUNT_SERVICE, name = "UpdatePhoneNumberResponse")
    public JAXBElement<UpdatePhoneNumberResponse> createUpdatePhoneNumberResponse(
            UpdatePhoneNumberResponse value) {
        return new JAXBElement<>(
                QNames.UpdatePhoneNumberResponse, UpdatePhoneNumberResponse.class, null, value);
    }
}
