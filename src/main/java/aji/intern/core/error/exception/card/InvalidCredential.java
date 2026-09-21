package aji.intern.core.error.exception.card;

import aji.intern.core.error.ServiceException;

public class InvalidCredential extends ServiceException {
    public InvalidCredential() {
        super("01", "Card Number or PIN is invalid", "ws", true);
    }
}
