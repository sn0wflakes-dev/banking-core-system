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
@XmlRootElement(name = "ActivateCardResponse", namespace = WebserviceEndpoint.NAMESPACE_CARD_SERVICE)
public class ActivateCardResponse {

    @XmlElement(name = "ResponseHeader", namespace = "")
    private ResponseHeader header;

    @XmlElement(name = "ActivateCardData", namespace = "")
    private ActivateCardData data;

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "ActivateCardResponseData", namespace = WebserviceEndpoint.NAMESPACE_CARD_SERVICE)
    public static class ActivateCardData {
        @XmlElement(name = "cardNumber", namespace = "")
        private String cardNumber;

        @XmlElement(name = "status", namespace = "")
        private String status;
    }
}
