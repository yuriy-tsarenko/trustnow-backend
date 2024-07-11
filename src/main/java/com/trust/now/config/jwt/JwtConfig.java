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

    @Value("${application.jwt.secretKey:no-key}")
    private String tokenKey;

    @Value("${application.jwt.tokenExpirationAfterDays:1}")
    private Integer tokenExpirationAfterDays;

    @Value("${application.jwt.tokenPrefix:NoPrefix}")
    private String tokenPrefix;

    @Bean
    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(tokenKey.getBytes());
    }
}