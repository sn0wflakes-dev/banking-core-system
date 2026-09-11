package aji.intern.core.rest.controller;

import aji.intern.core.rest.dto.RequestHeader;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;

@ControllerAdvice
public class RequestBodyAdvice extends RequestBodyAdviceAdapter {

    private static final Logger log = LogManager.getLogger(RequestBodyAdvice.class);

    @Override
    public boolean supports(
            @NonNull MethodParameter methodParameter,
            @NonNull Type targetType,
            @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object afterBodyRead(
            @NonNull Object body,
            @NonNull HttpInputMessage inputMessage,
            @NonNull MethodParameter parameter,
            @NonNull Type targetType,
            @NonNull Class<? extends HttpMessageConverter<?>> converterType) {

        BeanWrapper beanWrapper = new BeanWrapperImpl(body);

        if (!beanWrapper.isReadableProperty("requestHeader")) {
            return body;
        }

        Object header = beanWrapper.getPropertyValue("requestHeader");

        if (header instanceof RequestHeader requestHeader) {
            HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            servletRequest.setAttribute("messageId", requestHeader.getMessageId());

            log.info("Captured MessageId from Request Header at ReqBodyAdvice : {}",
                    servletRequest.getAttribute("messageId"));
        }

        return body;

    }
}
