package aji.intern.core.rest.dto.security;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RotateKeyResponse {
    private String serviceId;
    private String generatedKey;
}
