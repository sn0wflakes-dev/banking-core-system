package aji.intern.core.rest.dto.otp;

import aji.intern.core.rest.dto.RequestHeader;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyOtpRequest {
    private RequestHeader requestHeader;
    private VerifyOtpData verifyOtpData;

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VerifyOtpData {
        private String email;
        private String otp;
    }
}
