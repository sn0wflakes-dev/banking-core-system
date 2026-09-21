package aji.intern.core.error.exception.customer;

import aji.intern.core.error.ServiceException;

public class PhoneNumberAlreadyExist extends ServiceException {
    public PhoneNumberAlreadyExist() {
        super("09", "Mobile Number already reserved by another account", "ws", true);
    }
}
