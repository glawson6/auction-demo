package com.ttis.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tap on 6/18/16.
 */
@Configuration
@ConfigurationPropertiesBinding
@ConditionalOnClass({ EnableTTISSecurity.class})
@EnableConfigurationProperties(TTISSecurityProperties.class)
@AutoConfigureAfter({ DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class TTISSecurityAutoConfiguration {
    private final static Logger logger = LoggerFactory.getLogger(TTISSecurityAutoConfiguration.class);

    public TTISSecurityAutoConfiguration() { logger.info("TTISSecurityAutoConfiguration...");
    }

    @EnableWebSecurity
    @ConfigurationPropertiesBinding
    @ConditionalOnClass({ EnableTTISSecurity.class})
    //@ConditionalOnMissingBean(WebSecurityConfigurerAdapter.class)
    @EnableConfigurationProperties(TTISSecurityProperties.class)
    public static class TTISConfiguration extends WebSecurityConfigurerAdapter {

        public TTISConfiguration() {
            logger.info("Configuring TTISConfiguration.....");
        }

        @Autowired
        ObjectMapper objectMapper;

        @Bean
        RoleVoter roleVoter(){
            RoleVoter roleVoter = new RoleVoter();
            roleVoter.setRolePrefix(ttisSecurityProperties.getRolePrefix());
            return roleVoter;
        }

        @Bean
        AuthenticatedVoter authenticatedVoter(){
            return new AuthenticatedVoter();
        }

        @Bean
        TTISPermissionVoter rfPermissionVoter(){
            TTISPermissionVoter rfPermissionVoter = new TTISPermissionVoter();
            rfPermissionVoter.setPermissionPrefix(ttisSecurityProperties.getPermissionPrefix());
            return rfPermissionVoter;
        }

        @Bean
        AffirmativeBased affirmativeBased() {
            List<AccessDecisionVoter<?>> decisionVoters = new ArrayList<>();
            decisionVoters.add(rfPermissionVoter());
            decisionVoters.add(roleVoter());
            decisionVoters.add(authenticatedVoter());
            return new AffirmativeBased(decisionVoters);
        }

        @Bean
        // Code that uses this configuration can provide their own AuthenticationEntryPoint
        @ConditionalOnMissingBean(AuthenticationEntryPoint.class)
        AuthenticationEntryPoint defaultAuthenticationEntryPoint(){
            return new DefaultAuthenticationEntryPoint();
        }

        @Autowired(required = false)
        TTISSecurityProperties ttisSecurityProperties = new TTISSecurityProperties();

        @Bean
        AuthenticationUserDetailsService authenticationUserDetailsService(){
            if (null != ttisSecurityProperties.getAuthzTokenURL() && null != ttisSecurityProperties.getProductToken() ){
                logger.info("Using TTISUserDetailsServiceImpl...");

                TTISUserDetailsServiceImpl userDetailsService = new TTISUserDetailsServiceImpl();
                return userDetailsService;
            } else {
                logger.info("Using LocalUserDetailsServiceImpl with user file {}",ttisSecurityProperties.getUserFile());
                LocalUserDetailsServiceImpl userDetailsService = new LocalUserDetailsServiceImpl();
                userDetailsService.setUserDetailsJSONFile(ttisSecurityProperties.getUserFile());
                return userDetailsService;
            }

        }

        @Bean
        TTISAuthenticationManager ttisAuthenticationManager(){
            TTISAuthenticationManager TTISAuthenticationManager = new TTISAuthenticationManager();
            TTISAuthenticationManager.setAuthenticationProvider(preAuthProvider());
            return TTISAuthenticationManager;
        }

        @Bean
        TTISProcessingFilter ttisProcessingFilter(){
            TTISProcessingFilter filter = new TTISProcessingFilter();
            filter.setAuthenticationEntryPoint(defaultAuthenticationEntryPoint());
            filter.setIamAuthenticationManager(ttisAuthenticationManager());
            return filter;
        }

        @Bean
        AuthenticationProvider preAuthProvider(){
            PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
            provider.setPreAuthenticatedUserDetailsService(authenticationUserDetailsService());
            return provider;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable();
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            http.anonymous().disable();
            //http.exceptionHandling().accessDeniedHandler(new SecurityAccessDeniedHandler(this.objectMapper));
        }

        @Override
        protected void configure(AuthenticationManagerBuilder authBuilder){
            authBuilder.authenticationProvider(preAuthProvider());
        }

    }

    @Configuration
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    @AutoConfigureAfter(TTISConfiguration.class)
    public static class GlobalMethodSecurity  extends GlobalMethodSecurityConfiguration {

        @Bean
        TTISPermissionEvaluator rFPermissionEvaluator(){
            return new TTISPermissionEvaluator();
        }

        @Bean
        DefaultMethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler(){
            DefaultMethodSecurityExpressionHandler securityHandler = new DefaultMethodSecurityExpressionHandler();
            securityHandler.setPermissionEvaluator(rFPermissionEvaluator());
            return securityHandler;
        }

    }
}
