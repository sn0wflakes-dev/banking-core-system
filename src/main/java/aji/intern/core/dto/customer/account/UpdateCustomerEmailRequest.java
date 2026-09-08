package aji.intern.core.dto.customer.account;

import aji.intern.core.controller.WebserviceEndpoint;
import aji.intern.core.dto.SoapRequestHeader;
import jakarta.xml.bind.annotation.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "UpdateCustomerEmailRequest", namespace = WebserviceEndpoint.NAMESPACE_ACCOUNT_SERVICE)
public class UpdateCustomerEmailRequest {

    @XmlElement(name = "RequestHeader", namespace = "")
    private SoapRequestHeader header;

    @XmlElement(name = "UpdateCustomerEmailData", namespace = "")
    private UpdateCustomerEmailData data;

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(
            name = "UpdateCustomerEmailRequestData",
            namespace = WebserviceEndpoint.NAMESPACE_ACCOUNT_SERVICE
    )
    public static class UpdateCustomerEmailData {

        @XmlElement(name = "gcif", namespace = "")
        private String gcif;

        @XmlElement(name = "email", namespace = "")
        private String email;
    }
}
