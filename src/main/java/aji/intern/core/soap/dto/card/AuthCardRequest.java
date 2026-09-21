package aji.intern.core.soap.dto.card;

import aji.intern.core.soap.controller.WebserviceEndpoint;
import aji.intern.core.soap.dto.RequestHeader;
import jakarta.xml.bind.annotation.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "AuthCardRequest", namespace = WebserviceEndpoint.NAMESPACE_CARD_SERVICE)
public class AuthCardRequest {
    @XmlElement(name = "RequestHeader", namespace = "")
    private RequestHeader header;

    @XmlElement(name = "AuthCardData", namespace = "")
    private AuthCardData data;

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "AuthCardData", namespace = WebserviceEndpoint.NAMESPACE_CARD_SERVICE)
    public static class AuthCardData {
        @XmlElement(name = "cardNumber", namespace = "")
        private String cardNumber;

        @XmlElement(name = "pin", namespace = "")
        private String pin;
    }
}
