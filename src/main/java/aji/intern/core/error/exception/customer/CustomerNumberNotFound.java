package aji.intern.core.error.exception.customer;

import aji.intern.core.error.ServiceException;

public class CustomerNumberNotFound extends ServiceException {

    public CustomerNumberNotFound(String customerNumber) {
        // Not Found error code 04
        super("04", String.format("Customer account with number %s is not found", customerNumber), "ws", true);
    }
}
