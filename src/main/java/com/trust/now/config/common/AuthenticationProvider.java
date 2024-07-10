package com.trust.now.config.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationProvider {

    private final PasswordEncoder encoder;

    public UserAuthentication authenticate(UserAuthentication authentication, String password) throws AuthenticationException {
        log.info("Authenticating user: {}", authentication.getName());
        boolean matches = encoder.matches(password, authentication.getPassword());
        if (matches) {
            authentication.setAuthenticated(true);
            return authentication;
        }
        throw new BadCredentialsException("The password is incorrect");
    }

    public boolean supports(Class<?> authentication) {
        return UserAuthentication.class.isAssignableFrom(authentication);
    }
}
