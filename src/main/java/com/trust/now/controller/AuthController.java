package com.trust.now.controller;

import com.trust.now.dto.UserCredentialsDto;
import com.trust.now.service.auth.UserAuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {

    private final UserAuthService authService;

    @PostMapping("/login")
    public void login(@RequestBody UserCredentialsDto request, HttpServletResponse response) {
        log.info("Login request from: {}", request.username());
        try {
            response.addHeader(HttpHeaders.AUTHORIZATION, authService.issueAuthHeader(request));
            log.debug("JWT token successfully generated for user: {}", request.username());
        } catch (Exception e) {
            log.error("Authentication failed", e);
            throw new RuntimeException("Authentication failed", e);
        }
    }
}