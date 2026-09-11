package aji.intern.core.service;

import aji.intern.core.soap.dto.customer.account.UpdateCustomerEmailRequest;
import aji.intern.core.soap.dto.customer.account.UpdateCustomerEmailResponse;

public interface CustomerAccountService {
    UpdateCustomerEmailResponse updateCustomerEmail(UpdateCustomerEmailRequest request);
}
