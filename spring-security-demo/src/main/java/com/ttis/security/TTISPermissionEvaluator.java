package com.ttis.security;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Iterator;

/**
 * Created by tap on 6/18/16.
 */
public class TTISPermissionEvaluator  implements PermissionEvaluator {

    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        TTISUserDetails userDetails = (TTISUserDetails)authentication.getPrincipal();
        Iterator i$ = userDetails.getPermissions().iterator();

        GrantedAuthority authority;
        do {
            if(!i$.hasNext()) {
                return false;
            }

            authority = (GrantedAuthority)i$.next();
        } while(!authority.getAuthority().equals(permission));

        return true;
    }

    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        TTISUserDetails userDetails = (TTISUserDetails)authentication.getPrincipal();
        Iterator i$ = userDetails.getPermissions().iterator();

        GrantedAuthority authority;
        do {
            if(!i$.hasNext()) {
                return false;
            }

            authority = (GrantedAuthority)i$.next();
        } while(!authority.getAuthority().equals(permission));

        return true;
    }
}
