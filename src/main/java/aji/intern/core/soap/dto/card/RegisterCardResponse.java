package aji.intern.core.soap.dto.card;

import aji.intern.core.soap.controller.WebserviceEndpoint;
import aji.intern.core.soap.dto.ResponseHeader;
import jakarta.xml.bind.annotation.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RegisterCardResponse", namespace = WebserviceEndpoint.NAMESPACE_CARD_SERVICE)
public class RegisterCardResponse {
    @XmlElement(name = "ResponseHeader", namespace = "")
    private ResponseHeader header;

    @XmlElement(name = "RegisterCardData", namespace = "")
    private RegisterCardData data;

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "RegisterCardResponseData", namespace = WebserviceEndpoint.NAMESPACE_CARD_SERVICE)
    public static class RegisterCardData {
        @XmlElement(name = "customerNumber", namespace = "")
        private String customerNumber;

        @XmlElement(name = "cif", namespace = "")
        private String cif;

        @XmlElement(name = "cardNumber", namespace = "")
        private String cardNumber;

        @XmlElement(name = "expiryDate", namespace = "")
        private String expiryDate;

        @XmlElement(name = "status", namespace = "")
        private String status;
    }
}
