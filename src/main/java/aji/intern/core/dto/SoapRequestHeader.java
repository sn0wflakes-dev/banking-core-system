package aji.intern.core.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class SoapRequestHeader {

    @XmlElement(name = "messageId", required = true, namespace = "")
    private String messageId;

    @XmlElement(name = "channelId", required = true, namespace = "")
    private String channelId;

    @XmlElement(name = "sequenceNumber", required = true, namespace = "")
    private String sequenceNumber;

    @XmlElement(name = "transactionDate", required = true, namespace = "")
    private String transactionDate;

    @XmlElement(name = "transactionTime", required = true, namespace = "")
    private String transactionTime;
}
