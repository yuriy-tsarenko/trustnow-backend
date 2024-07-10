package com.trust.now.config.jwt;

import com.trust.now.config.common.UserAuthentication;
import com.trust.now.service.auth.UserAuthService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class UserJwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserAuthService userAuthService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.debug("JWT authentication filter");
        log.debug("Request: {}", request.getRequestURI());
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
            try {
                UserAuthentication userAuthentication = userAuthService.
                SecurityContextHolder.getContext().setAuthentication(userAuthentication);
            } catch (JwtException e) {
                log.error("Token cannot be parsed: {}", e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}