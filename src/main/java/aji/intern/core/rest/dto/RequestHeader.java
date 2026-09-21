package aji.intern.core.rest.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestHeader {
    private String messageId;
    private String serviceId;
    private String serviceType;
    private String sequenceNumber;
    private String transactionDate;
    private String transactionTime;
}
