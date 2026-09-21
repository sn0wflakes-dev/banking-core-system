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
@XmlRootElement(name = "ActivateCardRequest", namespace = WebserviceEndpoint.NAMESPACE_CARD_SERVICE)
public class ActivateCardRequest {

    @XmlElement(name = "RequestHeader", namespace = "")
    private RequestHeader header;

    @XmlElement(name = "ActivateCardData", namespace = "")
    private ActivateCardData data;

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "ActivateCardData", namespace = WebserviceEndpoint.NAMESPACE_CARD_SERVICE)
    public static class ActivateCardData {
        @XmlElement(name = "cardNumber", namespace = "")
        private String cardNumber;
    }
}
