package aji.intern.core.service;

import aji.intern.core.rest.dto.otp.GenerateOtpRequest;
import aji.intern.core.rest.dto.otp.GenerateOtpResponse;
import aji.intern.core.rest.dto.otp.VerifyOtpRequest;
import aji.intern.core.rest.dto.otp.VerifyOtpResponse;

public interface OtpService {
    GenerateOtpResponse generateOtp(GenerateOtpRequest request);
    VerifyOtpResponse verifyOtp(VerifyOtpRequest request);
}
