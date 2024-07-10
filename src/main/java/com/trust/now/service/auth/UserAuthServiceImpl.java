package com.trust.now.service.auth;

import com.trust.now.config.common.AuthenticationProvider;
import com.trust.now.config.common.UserAuthentication;
import com.trust.now.dto.UserCredentialsDto;
import com.trust.now.entity.UserAccountEntity;
import com.trust.now.mapper.UserMapper;
import com.trust.now.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
class UserAuthServiceImpl implements UserAuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationProvider authProvider;
    private final AuthTokenService authTokenService;

    @Override
    public UserAuthentication doAuth(UserCredentialsDto credentials) {
        log.info("Authenticating user: {}", credentials.username());
        UserAuthentication user = loadUserByUsername(credentials.username());
        return authProvider.authenticate(user, credentials.password());
    }

    @Override
    public UserAuthentication doAuth(String authHeader) {
        log.info("Authenticating user by token");
        return authTokenService.parseAuthHeader(authHeader);
    }

    @Override
    public String issueAuthHeader(UserCredentialsDto credentials) {
        UserAuthentication authentication = doAuth(credentials);
        return authTokenService.generateAuthHeader(authentication);
    }


    private UserAuthentication loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading user by username: {}", username);
        UserAccountEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return userMapper.toAuthUser(userEntity);
    }
}
