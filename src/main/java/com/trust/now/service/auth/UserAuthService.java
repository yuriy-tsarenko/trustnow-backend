package com.trust.now.service.auth;

import com.trust.now.config.common.UserAuthentication;
import com.trust.now.dto.UserCredentialsDto;

public interface UserAuthService {
    UserAuthentication doAuth(UserCredentialsDto credentials);

    UserAuthentication doAuth(String authHeader);

    String issueAuthHeader(UserCredentialsDto credentials);
}
