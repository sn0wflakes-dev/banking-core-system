package aji.intern.core.soap.controller;

import aji.intern.core.soap.dto.customer.account.ObjectFactory;
import aji.intern.core.soap.dto.customer.account.UpdateCustomerEmailRequest;
import aji.intern.core.soap.dto.customer.account.UpdateCustomerEmailResponse;
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

    private static final Logger log = LogManager.getLogger(AccountController.class);

    private final CustomerAccountService service;
    private final ObjectFactory objectFactory;

    public AccountController(CustomerAccountService service) {
        this.service = service;
        this.objectFactory = new ObjectFactory();
    }

    @PayloadRoot(namespace = WebserviceEndpoint.NAMESPACE_ACCOUNT_SERVICE, localPart = "UpdateCustomerEmailRequest")
    @ResponsePayload
    public JAXBElement<UpdateCustomerEmailResponse> updateCustomerEmailEndpoint(@RequestPayload JAXBElement<UpdateCustomerEmailRequest> request) {
        log.debug("REQUEST IN!");
        UpdateCustomerEmailResponse response = service.updateCustomerEmail(request.getValue());
        return objectFactory.createUpdateCustomerEmailResponse(response);
    }
}
