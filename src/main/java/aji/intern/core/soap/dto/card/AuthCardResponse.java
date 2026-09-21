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
@XmlRootElement(name = "AuthCardResponse", namespace = WebserviceEndpoint.NAMESPACE_CARD_SERVICE)
public class AuthCardResponse {
    @XmlElement(name = "ResponseHeader", namespace = "")
    private ResponseHeader header;

    @XmlElement(name = "AuthCardData", namespace = "")
    private AuthCardData data;

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "AuthCardResponseData", namespace = WebserviceEndpoint.NAMESPACE_CARD_SERVICE)
    public static class AuthCardData {
        @XmlElement(name = "customerNumber", namespace = "")
        private String customerNumber;

        @XmlElement(name = "name", namespace = "")
        private String name;

        @XmlElement(name = "address", namespace = "")
        private String address;

        @XmlElement(name = "cif", namespace = "")
        private String cif;

        @XmlElement(name = "cardNumber", namespace = "")
        private String cardNumber;
    }
}
