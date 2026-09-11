package aji.intern.core.rest.dto.security;

import aji.intern.core.rest.dto.RequestHeader;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterServiceRequest {
    private RequestHeader requestHeader;
    private RegisterServiceData registerServiceData;

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RegisterServiceData {
        private String serviceId;
    }
}
