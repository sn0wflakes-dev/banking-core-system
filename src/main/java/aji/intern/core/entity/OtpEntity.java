package aji.intern.core.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;

@RedisHash("OTP")
@Getter
@Setter
@AllArgsConstructor
public class OtpEntity implements Serializable {
    @Id
    private String email;
    private String otpCode;

    @TimeToLive
    private long expire;
}
