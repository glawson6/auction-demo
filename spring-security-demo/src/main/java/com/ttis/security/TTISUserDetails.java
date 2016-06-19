package com.ttis.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created by tap on 6/18/16.
 */
public interface TTISUserDetails extends UserDetails {
    Collection<? extends GrantedAuthority> getPermissions();
}
