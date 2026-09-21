package aji.intern.core.error.exception.card;

import aji.intern.core.error.ServiceException;

public class CardNumberNotFound extends ServiceException {
    public CardNumberNotFound(String cardNumber) {
        super("04", String.format("Card with card number (%s) is not found", cardNumber), "ws", true);
    }
}
