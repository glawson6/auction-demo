package com.ttis.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by tap on 6/18/16.
 */
public class TTISUser implements TTISUserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getPermissions() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
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
        return false;
    }
}
