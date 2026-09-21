package aji.intern.core.soap.dto.account;

import aji.intern.core.soap.controller.WebserviceEndpoint;
import aji.intern.core.soap.dto.ResponseHeader;
import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "UpdatePhoneNumberResponse", namespace = WebserviceEndpoint.NAMESPACE_ACCOUNT_SERVICE)
public class UpdatePhoneNumberResponse {
    @XmlElement(name = "ResponseHeader", namespace = "")
    private ResponseHeader header;

    @XmlElement(name = "UpdatePhoneNumberData", namespace = "")
    private UpdatePhoneNumberData data;

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "UpdatePhoneNumberResponseData", namespace = WebserviceEndpoint.NAMESPACE_ACCOUNT_SERVICE)
    public static class UpdatePhoneNumberData {

        @XmlElement(name = "cif", namespace = "")
        private String cif;

        @XmlElement(name = "updatedPhoneNumber", namespace = "")
        private String updatedPhoneNumber;
    }
}
