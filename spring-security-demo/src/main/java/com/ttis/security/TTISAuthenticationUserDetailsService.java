package com.ttis.security;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by tap on 6/18/16.
 */
public class TTISAuthenticationUserDetailsService implements AuthenticationUserDetailsService {
    @Override
    public UserDetails loadUserDetails(Authentication token) throws UsernameNotFoundException {
        String userName = (String)token.getPrincipal();
        if(userName == null) {
            throw new UsernameNotFoundException("Cannot find username in the system.");
        } else {
            try {
                HttpHeaders e = new HttpHeaders();
                e.add("uid", userName);
//                e.add("productToken", this.getProductToken());
//                HttpEntity requestEntity = new HttpEntity(e);
//                RestTemplate restTemplate = new RestTemplate();
//                ResponseEntity response = restTemplate.exchange(this.getAuthzTokenURL(), HttpMethod.GET, requestEntity, AuthzToken.class, new Object[0]);
//                AuthzToken authzToken = (AuthzToken)response.getBody();
//                authzToken.setVersion("v3");
                return new TTISUser(new TTISUserToken());
            } catch (Exception var8) {
                throw new RuntimeException("Cannot load permissions for user "+userName);
            }
        }
    }
}
