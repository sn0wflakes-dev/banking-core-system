package aji.intern.core.error.exception.otp;

import aji.intern.core.error.ServiceException;

public class InvalidOtp extends ServiceException {
    public InvalidOtp() {
        super("04", "Invalid OTP", "ws", true);
    }
}
