package aji.intern.core.soap.dto.account;

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
@XmlRootElement(name = "UpdatePhoneNumberRequest", namespace = WebserviceEndpoint.NAMESPACE_ACCOUNT_SERVICE)
public class UpdatePhoneNumberRequest {
    @XmlElement(name = "RequestHeader", namespace = "")
    private RequestHeader header;

    @XmlElement(name = "UpdatePhoneNumberData", namespace = "")
    private UpdatePhoneNumberData data;

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "UpdatePhoneNumberData", namespace = WebserviceEndpoint.NAMESPACE_ACCOUNT_SERVICE)
    public static class UpdatePhoneNumberData {

        @XmlElement(name = "cif", namespace = "")
        private String cif;

        @XmlElement(name = "phoneNumber", namespace = "")
        private String phoneNumber;
    }
}
