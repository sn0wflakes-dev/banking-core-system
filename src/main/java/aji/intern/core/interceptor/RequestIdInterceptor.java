package aji.intern.core.interceptor;

import org.apache.logging.log4j.ThreadContext;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.xml.transform.TransformerObjectSupport;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class RequestIdInterceptor extends TransformerObjectSupport implements EndpointInterceptor {

    private static final String messageIdCtx = "messageId";

    @Override
    public boolean handleRequest(MessageContext messageContext, @NonNull Object endpoint) throws Exception {
        SoapMessage message = (SoapMessage) messageContext.getRequest();
        Document doc = message.getDocument();

        NodeList node = doc.getElementsByTagName(messageIdCtx);
        if (node.getLength() > 0) {
            String messageId = node.item(0).getTextContent();
            ThreadContext.put(messageIdCtx, messageId);
        } else {
            System.out.println("Unable to capture messageId");
        }
        return true;
    }

    @Override
    public boolean handleResponse(@NonNull MessageContext messageContext, @NonNull Object endpoint) throws Exception {
        return false;
    }

    @Override
    public boolean handleFault(@NonNull MessageContext messageContext, @NonNull Object endpoint) throws Exception {
        return false;
    }

    @Override
    public void afterCompletion(@NonNull MessageContext messageContext, @NonNull Object endpoint, @Nullable Exception ex) throws Exception {
        ThreadContext.remove(messageIdCtx);
    }
}
