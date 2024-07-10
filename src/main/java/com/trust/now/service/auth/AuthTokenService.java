package com.trust.now.service.auth;

import com.trust.now.config.common.UserAuthentication;

public interface AuthTokenService {
    String generateToken(UserAuthentication authentication);

    String generateAuthHeader(UserAuthentication authentication);

    UserAuthentication parseToken(String token);

    UserAuthentication parseAuthHeader(String header);
}
