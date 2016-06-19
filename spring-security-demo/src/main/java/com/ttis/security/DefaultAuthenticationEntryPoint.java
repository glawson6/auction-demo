package com.ttis.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Gregory Lawson on 3/1/16.
 */
public class DefaultAuthenticationEntryPoint extends TTISAuthenticationEntryPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAuthenticationEntryPoint.class);

    private UrlPathHelper urlPathHelper = new UrlPathHelper();

    @Override
    protected Object createRemoteResponse(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        RemoteError error;
        if (authException instanceof DisabledException) {
            LOGGER.error("Account is disabled", authException);
            error = new RemoteError(RemoteErrorCodes.ACCOUNT_IS_DISABLED, authException.getMessage());
        } else if (authException instanceof InsufficientAuthenticationException) {
            LOGGER.error("uid not present in the request", authException);
            error = new RemoteError(RemoteErrorCodes.MISSING_UID_HEADER, authException.getMessage());
        } else {
            LOGGER.error("Exception during Authentication", authException);
            error = new RemoteError(RemoteErrorCodes.INVALID_USER_CREDENTIALS, authException.getMessage());
        }
        RemoteResponse remoteResponse = new RemoteResponse(error);
        remoteResponse.setAction(urlPathHelper.getLookupPathForRequest(request));
        return remoteResponse;
    }
}
