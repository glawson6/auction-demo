package com.ttis.security;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TTISProcessingFilter extends GenericFilterBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(TTISProcessingFilter.class);

    public static final String HEADER_UID = "uid";

    private AuthenticationEntryPoint authenticationEntryPoint;

    private AuthenticationManager authenticationManager;

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;
        String authHeaderContent = request.getHeader(HEADER_UID);
        String decoded = new String(Base64.decodeBase64(authHeaderContent));
        String user = decoded.substring(0, decoded.indexOf(":"));
        String password = decoded.substring(decoded.indexOf(":")+1);
        LOGGER.debug("uid {} user {} password {}", new Object[]{authHeaderContent, user, password});
        if (StringUtils.isNotEmpty(authHeaderContent)) {

            try {
            //    PreAuthenticatedAuthenticationToken authToken = new PreAuthenticatedAuthenticationToken(authHeaderContent, "N/A" );
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, password);
                Authentication authentication = authenticationManager.authenticate(authToken);
                PreAuthenticatedAuthenticationToken preAuthToken = new PreAuthenticatedAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(),authentication.getAuthorities());
                preAuthToken.setAuthenticated(true);
                LOGGER.debug("Granted authories authentication {}",authentication.getAuthorities().toString());
                LOGGER.debug("Granted authories preAuthToken {}",preAuthToken.getAuthorities().toString());
                //authentication.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(preAuthToken);

            } catch (AuthenticationException ae) {
                LOGGER.error("Error while authenticating the user {}", authHeaderContent, ae);
                authenticationEntryPoint.commence(request, response, ae);
                return;
            } catch (Exception e){
                LOGGER.error("Error while authenticating the user {}", authHeaderContent, e);
                authenticationEntryPoint.commence(request, response, new InternalAuthenticationServiceException(e.getMessage(),e));
                return;
            }
        } else {
            LOGGER.debug("uid received is {} null or empty", authHeaderContent);
            authenticationEntryPoint.commence(request, response, new InsufficientAuthenticationException("uid not found in the request"));
            return;
        }
        filterChain.doFilter(req, resp);
    }

    @Override
    protected void initFilterBean() throws ServletException {
        Assert.notNull(authenticationEntryPoint, "An AuthenticationEntryPoint must be set");
        Assert.notNull(authenticationManager, "An AuthenticationManager must be set");
    }

    public AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return authenticationEntryPoint;
    }

    public void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    public AuthenticationManager getIamAuthenticationManager() {
        return authenticationManager;
    }

    public void setIamAuthenticationManager(AuthenticationManager iamAuthenticationManager) {
        this.authenticationManager = iamAuthenticationManager;
    }
}
