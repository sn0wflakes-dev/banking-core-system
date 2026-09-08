package aji.intern.core.dto.customer.account;

import aji.intern.core.controller.WebserviceEndpoint;
import aji.intern.core.dto.SoapResponseHeader;
import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "UpdateCustomerEmailResponse", namespace = WebserviceEndpoint.NAMESPACE_ACCOUNT_SERVICE)
public class UpdateCustomerEmailResponse {

    @XmlElement(name = "ResponseHeader", namespace = "")
    private SoapResponseHeader header;

    @XmlElement(name = "UpdateCustomerEmailData", namespace = "")
    private UpdateCustomerEmailData data;

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(
            name = "UpdateCustomerEmailResponseData",
            namespace = WebserviceEndpoint.NAMESPACE_ACCOUNT_SERVICE
    )
    public static class UpdateCustomerEmailData {

        @XmlElement(name = "gcif", namespace = "")
        private String gcif;

        @XmlElement(name = "updatedEmail", namespace = "")
        private String updatedEmail;
    }
}
