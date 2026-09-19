package aji.intern.core.rest.controller.key;

import aji.intern.core.rest.dto.WebResponse;
import aji.intern.core.rest.dto.security.RegisterServiceRequest;
import aji.intern.core.rest.dto.security.RegisterServiceResponse;
import aji.intern.core.rest.dto.security.RetrieveKeyRequest;
import aji.intern.core.rest.dto.security.RetrieveKeyResponse;
import aji.intern.core.service.KeyRotationService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@Controller
@RequestMapping(path = "/api/key")
public class KeyRotationController {

    private static final Logger log = LogManager.getLogger(KeyRotationController.class);

    private final KeyRotationService service;

    public KeyRotationController(KeyRotationService service) {
        this.service = service;
    }

    @PostMapping(
            path = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
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

        // TODO : change response entity to return 201 created
        return ResponseEntity.ok(apiRes);
    }

    @GetMapping(
            path = "/{serviceId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<RetrieveKeyResponse>> retrieveKeyEndpoint(
            @PathVariable String serviceId,
            HttpServletRequest httpRequest) {

        String messageId = httpRequest.getHeader("X-Message-ID");
        RetrieveKeyRequest request = new RetrieveKeyRequest(serviceId);
        RetrieveKeyResponse result = service.retrieveKey(request);

        WebResponse<RetrieveKeyResponse> apiRes = WebResponse.<RetrieveKeyResponse>builder()
                .header(WebResponse.ResponseHeader.builder()
                        .messageId(messageId)
                        .timestamp(Instant.now().toString())
                        .build())
                .data(result)
                .build();

        return ResponseEntity.ok(apiRes);
    }
}
