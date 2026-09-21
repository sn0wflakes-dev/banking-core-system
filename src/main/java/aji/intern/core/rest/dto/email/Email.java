package aji.intern.core.rest.dto.email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Email {
    private String customerName;
    private String email;
    private String otp;
    private String operation;
    private String expiry;
}
