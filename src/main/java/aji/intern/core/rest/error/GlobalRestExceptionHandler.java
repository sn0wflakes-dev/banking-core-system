package aji.intern.core.rest.error;

import aji.intern.core.error.ServiceException;
import aji.intern.core.error.exception.key.ServiceIdAlreadyReserved;
import aji.intern.core.rest.dto.WebResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;

@ControllerAdvice
public class GlobalRestExceptionHandler {

    private static final Logger log = LogManager.getLogger(GlobalRestExceptionHandler.class);

    @ExceptionHandler(ServiceIdAlreadyReserved.class)
    public ResponseEntity<WebResponse<String>> handleServiceException(ServiceException ex, HttpServletRequest http) {
        log.error("Failed to make request. Reason : {}", ex.getMessage());
        String messageId = (String) http.getAttribute("messageId");
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(WebResponse.<String>builder()
                        .header(WebResponse.ResponseHeader.builder()
                                .messageId(messageId)
                                .timestamp(OffsetDateTime.now().toString())
                                .build())
                        .error(WebResponse.ErrorMessage.builder()
                                .errorOrigin(ex.getOrigin())
                                .responseCode(ex.getCode())
                                .message(ex.getMessage())
                                .build())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<WebResponse<String>> handleGenericException(Exception ex, HttpServletRequest http) {
        log.error("Failed to make request. Reason : Internal server error, details : {}", ex.getMessage());
        String messageId = (String) http.getAttribute("messageId");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(WebResponse.<String>builder()
                        .header(WebResponse.ResponseHeader.builder()
                                .messageId(messageId)
                                .timestamp(OffsetDateTime.now().toString())
                                .build())
                        .error(WebResponse.ErrorMessage.builder()
                                .errorOrigin("ws")
                                .responseCode("99")
                                .message("500 Internal Server Error")
                                .build())
                        .build());
    }
}
