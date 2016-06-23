package com.ttis.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;

import static java.util.stream.Collectors.toList;

/**
 * Created by tap on 6/18/16.
 */
public class TTISUser implements TTISUserDetails {

    private TTISUserToken ttisUserToken;

    public TTISUser(TTISUserToken ttisUserToken) {
        this.ttisUserToken = ttisUserToken;
    }

    Function<String, GrantedAuthority> grantedAuthFunction = (permAuth) -> {
        return new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return permAuth;
            }
        };
    };

    @Override
    public Collection<? extends GrantedAuthority> getPermissions() {
        return ttisUserToken.getPermissions().stream().map(grantedAuthFunction).collect(toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return ttisUserToken.getRoles().stream().map(grantedAuthFunction).collect(toList());
    }

    @Override
    public String getPassword() {
        return ttisUserToken.getPassword();
    }

    @Override
    public String getUsername() {
        return ttisUserToken.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
