package com.ttis.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by tap on 6/19/16.
 */
@Configuration
@ImportResource(locations = {"classpath:ldap-security-context.xml"})
public class LDAPXMLSecurityConfiguration {
}
