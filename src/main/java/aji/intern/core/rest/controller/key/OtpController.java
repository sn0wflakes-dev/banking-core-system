package aji.intern.core.rest.controller.key;

import aji.intern.core.rest.dto.WebResponse;
import aji.intern.core.rest.dto.otp.GenerateOtpRequest;
import aji.intern.core.rest.dto.otp.GenerateOtpResponse;
import aji.intern.core.rest.dto.otp.VerifyOtpRequest;
import aji.intern.core.rest.dto.otp.VerifyOtpResponse;
import aji.intern.core.service.OtpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;

@Controller
@RequestMapping(path = "/api/otp")
public class OtpController {

    private final OtpService service;

    public OtpController(OtpService service) {
        this.service = service;
    }

    @PostMapping(
            path = "/generate",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<GenerateOtpResponse>> generateOtpEndpoint(@RequestBody GenerateOtpRequest request) {

        GenerateOtpResponse result = service.generateOtp(request);

        WebResponse<GenerateOtpResponse> apiRes = WebResponse.<GenerateOtpResponse>builder()
                .header(WebResponse.ResponseHeader.builder()
                        .messageId(request.getRequestHeader().getMessageId())
                        .timestamp(Instant.now().toString())
                        .build())
                .data(result)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiRes);
    }

    @PostMapping(
            path = "/verify",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<VerifyOtpResponse>> verifyOtpEndpoint(@RequestBody VerifyOtpRequest request) {

        VerifyOtpResponse result = service.verifyOtp(request);

        WebResponse<VerifyOtpResponse> apiRes = WebResponse.<VerifyOtpResponse>builder()
                .header(WebResponse.ResponseHeader.builder()
                        .messageId(request.getRequestHeader().getMessageId())
                        .timestamp(Instant.now().toString())
                        .build())
                .data(result)
                .build();

        return ResponseEntity.ok(apiRes);
    }
}
