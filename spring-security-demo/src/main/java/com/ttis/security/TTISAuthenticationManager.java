package com.ttis.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class TTISAuthenticationManager implements AuthenticationManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(TTISAuthenticationManager.class);

    private AuthenticationProvider authenticationProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        LOGGER.info("uid received is {}", authentication.getPrincipal());

        Authentication auth = null;
        try {
            if(authenticationProvider.supports(authentication.getClass())) {

                auth = authenticationProvider.authenticate(authentication);

            } else {
                throw new ProviderNotFoundException("Provider not support the the authentication  "+ authentication.getClass());
            }
        } catch (AuthenticationException ex ){
            LOGGER.debug("Exception while loading userdetails for user {}", authentication.getPrincipal());
            throw ex;
        }

        if(auth == null) {
            LOGGER.warn(" Failed to fetch user details for user {} ", authentication.getPrincipal());
            throw new RuntimeException("Failed to fetch user details for user" + authentication.getPrincipal());
        }
        return auth;

    }

    public AuthenticationProvider getAuthenticationProvider() {
        return authenticationProvider;
    }

    public void setAuthenticationProvider(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }
}
