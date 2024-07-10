package com.trust.now.service.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trust.now.config.common.UserAuthentication;
import com.trust.now.config.jwt.JwtConfig;
import com.trust.now.dto.UserAuthDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements AuthTokenService {
    private static final String HEADER_DELIMITER = " ";

    private final SecretKey secretKey;
    private final JwtConfig config;
    private final ObjectMapper objectMapper;

    @Value("${spring.application.name}")
    private String issuer;

    @Override
    public String generateAuthHeader(UserAuthentication authentication) {
        String token = generateToken(authentication);
        return String.join(HEADER_DELIMITER, config.getTokenPrefix(), token);
    }

    @Override
    public UserAuthentication parseAuthHeader(String header) {
        String token = header.startsWith(config.getTokenPrefix())
                ? header.replace(config.getTokenPrefix(), "").trim()
                : header;
        return parseToken(token);
    }

    @Override
    public String generateToken(UserAuthentication authentication) {
        Date now = new Date();
        Date expirationDate = Date.from(Instant.now().plus(config.getTokenExpirationAfterDays(), ChronoUnit.DAYS));
        try {
            return Jwts.builder()
                    .subject(authentication.getName())
                    .claim("details", objectMapper.writeValueAsString(authentication.getDetails()))
                    .issuedAt(now)
                    .expiration(expirationDate)
                    .signWith(secretKey)
                    .issuer(issuer)
                    .compact();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing JSON", e);
        }
    }

    public UserAuthentication parseToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser()
                .decryptWith(secretKey)
                .requireIssuer(issuer)
                .build()
                .parseSignedClaims(token);

        Claims claims = claimsJws.getPayload();
        String username = claims.getSubject();
        String detailsJson = claims.get("details", String.class);

        try {
            UserAuthDto userDto = objectMapper.readValue(detailsJson, UserAuthDto.class);
            UserAuthentication authentication = new UserAuthentication(username, null, userDto.roles().stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList());
            authentication.setDetails(userDto);
            return authentication;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing JSON", e);
        }
    }
}