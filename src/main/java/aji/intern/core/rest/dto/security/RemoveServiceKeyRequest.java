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
public class RemoveServiceKeyRequest {
    private RequestHeader requestHeader;
    private RemoveServiceData removeServiceData;

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RemoveServiceData {
        private String serviceId;
    }
}
