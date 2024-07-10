package com.trust.now.mapper;

import com.trust.now.config.common.UserAuthentication;
import com.trust.now.dto.UserAuthDto;
import com.trust.now.entity.UserAccountEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserAuthentication toAuthUser(UserAccountEntity source) {
        UserAuthentication target = new UserAuthentication(source.getUsername(), source.getPassword());
        target.setAuthorities(source.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .toList());
        target.setDetails(toAuthDto(source));
        return target;
    }

    private UserAuthDto toAuthDto(UserAccountEntity source) {
        return new UserAuthDto(
                source.getId(),
                source.getUsername(),
                source.getFirstName(),
                source.getMiddleName(),
                source.getLastName(),
                source.getLastLogin(),
                source.getLastLoginIpAddress(),
                source.getLoginApp(),
                source.getLockoutTimeTo(),
                source.getIsEmailVerified(),
                source.getIsPhoneVerified(),
                source.getIsActive(),
                source.getRoles()
        );

    }
}
