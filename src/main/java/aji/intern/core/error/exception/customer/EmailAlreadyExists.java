package aji.intern.core.error.exception.customer;

import aji.intern.core.error.ServiceException;

public class EmailAlreadyExists extends ServiceException {
    public EmailAlreadyExists() {
        // Conflict error code 09
        super("09", "Email Address already reserved by another account", "ws", true);
    }
}
