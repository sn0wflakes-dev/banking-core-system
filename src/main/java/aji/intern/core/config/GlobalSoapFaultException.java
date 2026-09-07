package aji.intern.core.config;

import aji.intern.core.dto.ResponseHeader;
import aji.intern.core.error.ServiceException;
import jakarta.xml.bind.JAXBElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.jspecify.annotations.Nullable;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.server.endpoint.AbstractSoapFaultDefinitionExceptionResolver;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;

import javax.xml.namespace.QName;
import javax.xml.transform.Result;

@Configuration
public class GlobalSoapFaultException extends AbstractSoapFaultDefinitionExceptionResolver {

    private static final Logger log = LogManager.getLogger(GlobalSoapFaultException.class);

    private final Jaxb2Marshaller marshaller;

    public GlobalSoapFaultException(Jaxb2Marshaller jaxb2Marshaller) {
        this.marshaller = jaxb2Marshaller;
        setOrder(1);
    }

    @Override
    protected @Nullable SoapFaultDefinition getFaultDefinition(@Nullable Object endpoint, Exception ex) {
        SoapFaultDefinition definition = new SoapFaultDefinition();
        if (ex instanceof ServiceException se) {
            definition.setFaultCode(se.isClientFault() ? SoapFaultDefinition.CLIENT : SoapFaultDefinition.SERVER);
            definition.setFaultStringOrReason(se.getMessage());
        } else {
            definition.setFaultCode(SoapFaultDefinition.SERVER);
            definition.setFaultStringOrReason("Internal server error, please contact support");
        }
        return definition;
    }

    @Override
    protected void customizeFault(@Nullable Object endpoint, Exception ex, SoapFault fault) {
        String responseCode;
        String errorOrigin;
        String responseMessage;

        if (ex instanceof ServiceException se) {
            responseCode = se.getCode();
            errorOrigin = se.getOrigin();
            responseMessage = se.getMessage();
        } else {
            log.error("Unhandled exception in SOAP endpoint", ex);
            responseCode = "99";
            errorOrigin = "ws";
            responseMessage = "Internal server error, please contact support";
        }

        ResponseHeader responseHeader = ResponseHeader.builder()
                .responseCode(responseCode)
                .errorOrigin(errorOrigin)
                .responseMessage(responseMessage)
                .messageId(ThreadContext.get("messageId"))
                .build();

        SoapFaultDetail detail = fault.addFaultDetail();
        Result result = detail.getResult();

        JAXBElement<ResponseHeader> element = new JAXBElement<>(
                new QName("", "ResponseHeader"), ResponseHeader.class, responseHeader);

        marshaller.marshal(element, result);
    }
}
