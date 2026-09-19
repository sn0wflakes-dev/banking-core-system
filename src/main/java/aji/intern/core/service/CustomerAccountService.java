package aji.intern.core.service;

import aji.intern.core.soap.dto.customer.account.RegisterCustomerAccountRequest;
import aji.intern.core.soap.dto.customer.account.RegisterCustomerAccountResponse;
import aji.intern.core.soap.dto.customer.account.UpdateCustomerEmailRequest;
import aji.intern.core.soap.dto.customer.account.UpdateCustomerEmailResponse;

public interface CustomerAccountService {
    RegisterCustomerAccountResponse registerCustomerAccount(RegisterCustomerAccountRequest request);
    UpdateCustomerEmailResponse updateCustomerEmail(UpdateCustomerEmailRequest request);
}
