package aji.intern.core.rest.dto.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RotateKeyResponse {
    private String serviceId;
    private String generatedKey;
}
