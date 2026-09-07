package aji.intern.core.service;

import aji.intern.core.dto.customer.account.UpdateCustomerEmailRequest;
import aji.intern.core.dto.customer.account.UpdateCustomerEmailResponse;

public interface CustomerAccountService {
    UpdateCustomerEmailResponse updateCustomerEmail(UpdateCustomerEmailRequest request);
}
