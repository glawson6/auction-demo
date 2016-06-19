package com.ttis.security;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by tap on 6/18/16.
 */
public class TTISPermissionVoter implements AccessDecisionVoter<Object> {
    private String permissionPrefix = "PERMISSION_";

    public TTISPermissionVoter() {
    }

    public String getPermissionPrefix() {
        return this.permissionPrefix;
    }

    public void setPermissionPrefix(String permissionPrefix) {
        this.permissionPrefix = permissionPrefix;
    }

    public boolean supports(ConfigAttribute attribute) {
        return attribute.getAttribute() != null && attribute.getAttribute().startsWith(this.getPermissionPrefix());
    }

    public boolean supports(Class<?> clazz) {
        return true;
    }

    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        byte result = 0;
        if(authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            Collection authorities = this.extractAuthorities(authentication);
            Iterator i$ = attributes.iterator();

            while(true) {
                ConfigAttribute attribute;
                do {
                    if(!i$.hasNext()) {
                        return result;
                    }

                    attribute = (ConfigAttribute)i$.next();
                } while(!this.supports(attribute));

                result = -1;
                Iterator i$1 = authorities.iterator();

                while(i$1.hasNext()) {
                    GrantedAuthority authority = (GrantedAuthority)i$1.next();
                    if(attribute.getAttribute().equals(authority.getAuthority())) {
                        return 1;
                    }
                }
            }
        } else {
            return -1;
        }
    }

    Collection<? extends GrantedAuthority> extractAuthorities(Authentication authentication) {
        TTISUserDetails userDetails = (TTISUserDetails)authentication.getPrincipal();
        return userDetails.getPermissions();
    }
}
