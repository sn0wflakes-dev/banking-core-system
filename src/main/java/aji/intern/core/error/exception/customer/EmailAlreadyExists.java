package aji.intern.core.error.exception.customer;

import aji.intern.core.error.ServiceException;

public class EmailAlreadyExists extends ServiceException {
    public EmailAlreadyExists() {
        super("09", "Email Address already reserved by another account", "ws", true);
    }
}
