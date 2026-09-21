package aji.intern.core.soap.dto.account;

import aji.intern.core.soap.controller.WebserviceEndpoint;
import aji.intern.core.soap.dto.ResponseHeader;
import jakarta.xml.bind.annotation.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RegisterCustomerAccountResponse", namespace = WebserviceEndpoint.NAMESPACE_ACCOUNT_SERVICE)
public class RegisterCustomerAccountResponse {

    @XmlElement(name = "ResponseHeader", namespace = "")
    private ResponseHeader header;

    @XmlElement(name = "RegisterCustomerAccountData", namespace = "")
    private RegisterCustomerAccountData data;

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "RegisterCustomerAccountResponseData", namespace = WebserviceEndpoint.NAMESPACE_ACCOUNT_SERVICE)
    public static class RegisterCustomerAccountData {
        @XmlElement(name = "customerNumber", namespace = "")
        private String customerNumber;

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

        @XmlElement(name = "cif", namespace = "")
        private String cif;
    }

}
