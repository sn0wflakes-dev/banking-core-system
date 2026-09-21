package aji.intern.core.service;

import aji.intern.core.soap.dto.account.*;

public interface CustomerAccountService {
    RegisterCustomerAccountResponse registerCustomerAccount(RegisterCustomerAccountRequest request);
    UpdateCustomerEmailResponse updateCustomerEmail(UpdateCustomerEmailRequest request);
    UpdatePhoneNumberResponse updatePhoneNumber(UpdatePhoneNumberRequest request);
}
