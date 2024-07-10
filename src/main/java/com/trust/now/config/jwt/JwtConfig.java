package com.trust.now.config.jwt;

import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Getter
@Configuration
public class JwtConfig {

    @Value("${application.jwt.secretKey}")
    private String tokenKey;

    @Value("${application.jwt.tokenExpirationAfterDays}")
    private Integer tokenExpirationAfterDays;

    @Value("${application.jwt.tokenPrefix}")
    private String tokenPrefix;

    @Bean
    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(tokenKey.getBytes());
    }
}