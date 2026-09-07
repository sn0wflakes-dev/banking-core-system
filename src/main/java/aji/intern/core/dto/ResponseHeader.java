package aji.intern.core.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseHeader {

    @XmlElement(name = "responseCode", namespace = "")
    private String responseCode;

    @XmlElement(name = "messageId", required = true, namespace = "")
    private String messageId;

    @XmlElement(name = "errorOrigin", namespace = "")
    private String errorOrigin;

    @XmlElement(name = "responseMessage", namespace = "")
    private String responseMessage;
}
