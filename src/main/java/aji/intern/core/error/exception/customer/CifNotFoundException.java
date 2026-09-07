package aji.intern.core.error.exception.customer;

import aji.intern.core.error.ServiceException;

public class CifNotFoundException extends ServiceException {

    public CifNotFoundException(String cif) {
        super("10", String.format("Customer identification file (%s) is not found", cif), "ws", true);
    }
}
