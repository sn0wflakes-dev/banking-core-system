package aji.intern.core.rest.dto.security;

import aji.intern.core.rest.dto.RequestHeader;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RotateKeyRequest {
    private RequestHeader requestHeader;
    private RotateKeyData rotateKeyData;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RotateKeyData {
        private String serviceId;
    }
}
