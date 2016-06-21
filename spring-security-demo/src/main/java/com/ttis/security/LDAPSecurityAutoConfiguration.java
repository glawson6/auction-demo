package com.ttis.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tap on 6/20/16.
 */
@Configuration
@PropertySource({"classpath:ldap.properties"})
public class LDAPSecurityAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(LDAPSecurityAutoConfiguration.class);

    public LDAPSecurityAutoConfiguration() {
        logger.info("LDAPSecurityAutoConfiguration.....");
    }

    @Configuration
    @ImportResource(locations = {"classpath:ldap-security-context.xml"})
    @ConditionalOnProperty(prefix = "ldap.config", name = "java", havingValue = "false", matchIfMissing = true)
    public static class LDAPXMLSecurityConfiguration {
        public LDAPXMLSecurityConfiguration() {
            logger.info("Using LDAPXMLSecurityConfiguration!");
        }

        /*
         <bean id="contextSource"
          class="org.springframework.security.ldap.DefaultSpringSecurityContextSource">
        <constructor-arg value="${ldap.url}"/>
        <property name="userDn" value="${ldap.userDn}"/>
        <property name="password" value="${ldap.password}"/>
    </bean>

    <bean id="preAuthProvider"
          class="org.springframework.security.ldap.authentication.LdapAuthenticationProvider">
        <constructor-arg>
            <bean class="org.springframework.security.ldap.authentication.BindAuthenticator">
                <constructor-arg ref="contextSource"/>
                <property name="userDnPatterns">
                    <list><value>uid={0},cn=users</value></list>
                </property>
            </bean>
        </constructor-arg>
        <constructor-arg>
            <bean
                    class="org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator">
                <constructor-arg ref="contextSource"/>
                <constructor-arg value="cn=groups"/>
                <property name="groupRoleAttribute" value="cn"/>
                <property name="rolePrefix" value=""/>
            </bean>
        </constructor-arg>
    </bean>
         */
    }

    @Configuration
    @ConditionalOnProperty(prefix = "ldap.config", name = "java", havingValue = "true", matchIfMissing = true)
    public static class LDAPJavaSecurityConfiguration{

        public LDAPJavaSecurityConfiguration() {
            logger.info("Using LDAPJavaSecurityConfiguration!");
        }

        @Value("${ldap.userDn}")
        String ldapUserDn;

        @Value("${ldap.password}")
        String ldapPassword;

        @Value("${ldap.url}")
        String ldapUrl;

        @Bean(name = "contextSource")
        DefaultSpringSecurityContextSource defaultSpringSecurityContextSource(){
            DefaultSpringSecurityContextSource defaultSpringSecurityContextSource = new DefaultSpringSecurityContextSource(ldapUrl);
            defaultSpringSecurityContextSource.setUserDn(ldapUserDn);
            defaultSpringSecurityContextSource.setPassword(ldapPassword);
            return defaultSpringSecurityContextSource;
        }

        private static String[] userDnArray = {"uid={0},cn=users"};

        @Bean(name="bindAuthenticator")
        BindAuthenticator bindAuthenticator(@Qualifier("contextSource")DefaultSpringSecurityContextSource defaultSpringSecurityContextSource){
            BindAuthenticator bindAuthenticator = new BindAuthenticator(defaultSpringSecurityContextSource);
            bindAuthenticator.setUserDnPatterns(userDnArray);
            return bindAuthenticator;
        }

        @Bean(name="defaultLdapAuthoritiesPopulator")
        DefaultLdapAuthoritiesPopulator defaultLdapAuthoritiesPopulator(@Qualifier("contextSource")DefaultSpringSecurityContextSource defaultSpringSecurityContextSource){
            DefaultLdapAuthoritiesPopulator defaultLdapAuthoritiesPopulator = new DefaultLdapAuthoritiesPopulator(
                    defaultSpringSecurityContextSource , "cn=groups");
            defaultLdapAuthoritiesPopulator.setGroupRoleAttribute("cn");
            defaultLdapAuthoritiesPopulator.setRolePrefix("");
            return defaultLdapAuthoritiesPopulator;
        }

        @Bean(name="preAuthProvider")
        LdapAuthenticationProvider ldapAuthenticationProvider(@Qualifier("bindAuthenticator")BindAuthenticator bindAuthenticator,
                                                              DefaultLdapAuthoritiesPopulator defaultLdapAuthoritiesPopulator  ){
            LdapAuthenticationProvider ldapAuthenticationProvider = new LdapAuthenticationProvider(bindAuthenticator, defaultLdapAuthoritiesPopulator);
            return ldapAuthenticationProvider;
        }
    }

}
