package com.trust.now.config.common;

import com.trust.now.dto.UserAuthDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class UserAuthentication implements Authentication {

    private Object principal;
    private Object credentials;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean authenticated = false;
    private UserAuthDto details;

    public UserAuthentication(Object principal, Object credentials) {
        this(principal, credentials, null);
    }

    public UserAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        this.principal = principal;
        this.credentials = credentials;
        this.authorities = authorities;
    }

    @Override
    public String getName() {
        return this.principal.toString();
    }

    public String getPassword() {
        return this.credentials.toString();
    }
}
