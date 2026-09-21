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
public class GenerateOtpRequest {

    private RequestHeader requestHeader;
    private GenerateOtpData generateOtpData;

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GenerateOtpData {
        private String cif;
    }
}
