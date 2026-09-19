package aji.intern.core.soap.dto.customer.account;

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
@XmlRootElement(name = "RegisterCustomerAccountRequest", namespace = WebserviceEndpoint.NAMESPACE_ACCOUNT_SERVICE)
public class RegisterCustomerAccountRequest {
    @XmlElement(name = "RequestHeader", namespace = "")
    private RequestHeader header;

    @XmlElement(name = "RegisterCustomerAccountData", namespace = "")
    private RegisterCustomerAccountData data;

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "RegisterCustomerAccountData", namespace = WebserviceEndpoint.NAMESPACE_ACCOUNT_SERVICE)
    public static class RegisterCustomerAccountData {
        @XmlElement(name = "name", namespace = "")
        private String name;

        @XmlElement(name = "birthDate", namespace = "")
        private String birthDate;

        @XmlElement(name = "phoneNumber", namespace = "")
        private String phoneNumber;

        @XmlElement(name = "email", namespace = "")
        private String email;

        @XmlElement(name = "address", namespace = "")
        private String address;
    }
}
