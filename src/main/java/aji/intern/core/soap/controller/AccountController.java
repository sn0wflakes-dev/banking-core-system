package aji.intern.core.soap.controller;

import aji.intern.core.soap.dto.account.*;
import aji.intern.core.service.CustomerAccountService;
import jakarta.xml.bind.JAXBElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class AccountController {
    private final CustomerAccountService service;
    private final ObjectFactory objectFactory;

    public AccountController(CustomerAccountService service) {
        this.service = service;
        this.objectFactory = new ObjectFactory();
    }

    @PayloadRoot(namespace = WebserviceEndpoint.NAMESPACE_ACCOUNT_SERVICE, localPart = "UpdateCustomerEmailRequest")
    @ResponsePayload
    public JAXBElement<UpdateCustomerEmailResponse> updateCustomerEmailEndpoint(
            @RequestPayload JAXBElement<UpdateCustomerEmailRequest> request) {
        UpdateCustomerEmailResponse response = service.updateCustomerEmail(request.getValue());
        return objectFactory.createUpdateCustomerEmailResponse(response);
    }

    @PayloadRoot(namespace = WebserviceEndpoint.NAMESPACE_ACCOUNT_SERVICE, localPart = "RegisterCustomerAccountRequest")
    @ResponsePayload
    public JAXBElement<RegisterCustomerAccountResponse> registerCustomerAccountEndpoint(
            @RequestPayload JAXBElement<RegisterCustomerAccountRequest> request) {
        RegisterCustomerAccountResponse response = service.registerCustomerAccount(request.getValue());
        return objectFactory.createRegisterCustomerAccountResponse(response);
    }

    @PayloadRoot(namespace = WebserviceEndpoint.NAMESPACE_ACCOUNT_SERVICE, localPart = "UpdatePhoneNumberRequest")
    @ResponsePayload
    public JAXBElement<UpdatePhoneNumberResponse> updatePhoneNumberEndpoint(
            @RequestPayload JAXBElement<UpdatePhoneNumberRequest> request) {
        UpdatePhoneNumberResponse response = service.updatePhoneNumber(request.getValue());
        return objectFactory.createUpdatePhoneNumberResponse(response);
    }

}
