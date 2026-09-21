package aji.intern.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password4j.Argon2Password4jPasswordEncoder;

@Configuration
public class SpringSecurityConfig {

    @Bean
    public PasswordEncoder argon2Encoder() {
        return new Argon2Password4jPasswordEncoder();
    }

}
