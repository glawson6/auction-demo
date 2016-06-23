package com.ttis.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
/**
 * Created by tap on 6/18/16.
 */
public class TTISUserDetailsServiceImpl implements AuthenticationUserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TTISUserDetailsServiceImpl.class);


    @Override
    public UserDetails loadUserDetails(Authentication authentication) throws UsernameNotFoundException {

        String userName = (String)authentication.getPrincipal();
        LOGGER.debug("uid received is {}", userName);

        UserDetails principal = null;
        try {

               // principal = userDetailsService.loadUserDetails(authentication);

        } catch (UsernameNotFoundException ex ){
            LOGGER.error("Exception while loading userdetails for user {}", userName, ex);
            throw new UsernameNotFoundException(userName + " does not exist.");
        }

        return principal;
    }

}
