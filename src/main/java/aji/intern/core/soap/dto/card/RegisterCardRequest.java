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
@XmlRootElement(name = "RegisterCardRequest", namespace = WebserviceEndpoint.NAMESPACE_CARD_SERVICE)
public class RegisterCardRequest {
    @XmlElement(name = "RequestHeader", namespace = "")
    private RequestHeader header;

    @XmlElement(name = "RegisterCardData", namespace = "")
    private RegisterCardData data;

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "RegisterCardData", namespace = WebserviceEndpoint.NAMESPACE_CARD_SERVICE)
    public static class RegisterCardData {
        @XmlElement(name = "pin", namespace = "")
        private String pin;

        @XmlElement(name = "customerNumber", namespace = "")
        private String customerNumber;
    }
}
