package aji.intern.core.rest.controller.key;

import aji.intern.core.rest.dto.WebResponse;
import aji.intern.core.rest.dto.security.RegisterServiceRequest;
import aji.intern.core.rest.dto.security.RegisterServiceResponse;
import aji.intern.core.service.KeyRotationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;

@Controller
@RequestMapping(path = "/api/key")
public class KeyRotationController {

    private static final Logger log = LogManager.getLogger(KeyRotationController.class);

    private final KeyRotationService service;

    public KeyRotationController(KeyRotationService service) {
        this.service = service;
    }

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<RegisterServiceResponse>> registerServiceEndpoint(
            @RequestBody RegisterServiceRequest request) {

        RegisterServiceResponse response = service.registerService(request);

        WebResponse<RegisterServiceResponse> apiRes = WebResponse.<RegisterServiceResponse>builder()
                .header(WebResponse.ResponseHeader.builder()
                        .messageId(request.getRequestHeader().getMessageId())
                        .timestamp(Instant.now().toString())
                        .build())
                .data(response)
                .build();

        return ResponseEntity.ok(apiRes);
    }

}
