package com.ttis.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ldap.NameNotFoundException;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.security.ldap.SpringSecurityLdapTemplate;
import org.springframework.security.ldap.authentication.AbstractLdapAuthenticator;
import org.springframework.util.Assert;

/**
 * Created by tap on 6/19/16.
 */
public class PreAuthenticatedLDAPAuthenticator extends AbstractLdapAuthenticator {
    // ~ Static fields/initializers
    // =====================================================================================

    private static final Log logger = LogFactory
            .getLog(PreAuthenticatedLDAPAuthenticator.class);

    // ~ Instance fields
    // ================================================================================================

    private PasswordEncoder passwordEncoder = new LdapShaPasswordEncoder();
    private String passwordAttributeName = "userPassword";
    private boolean usePasswordAttrCompare = false;

    // ~ Constructors
    // ===================================================================================================

    public PreAuthenticatedLDAPAuthenticator(BaseLdapPathContextSource contextSource) {
        super(contextSource);
    }

    // ~ Methods
    // ========================================================================================================

    public DirContextOperations authenticate(final Authentication authentication) {
        Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, authentication,
                "Can only process UsernamePasswordAuthenticationToken objects");
        // locate the user and check the password

        DirContextOperations user = null;
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        SpringSecurityLdapTemplate ldapTemplate = new SpringSecurityLdapTemplate(
                getContextSource());

        for (String userDn : getUserDns(username)) {
            try {
                user = ldapTemplate.retrieveEntry(userDn, getUserAttributes());
            }
            catch (NameNotFoundException ignore) {
            }
            if (user != null) {
                break;
            }
        }

        if (user == null && getUserSearch() != null) {
            user = getUserSearch().searchForUser(username);
        }

        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Performing LDAP compare of password attribute '"
                    + passwordAttributeName + "' for user '" + user.getDn() + "'");
        }

        if (usePasswordAttrCompare && isPasswordAttrCompare(user, password)) {
            return user;
        }
        else if (isLdapPasswordCompare(user, ldapTemplate, password)) {
            return user;
        }
        throw new BadCredentialsException(messages.getMessage(
                "PasswordComparisonAuthenticator.badCredentials", "Bad credentials"));
    }

    private boolean isPasswordAttrCompare(DirContextOperations user, String password) {
        Object passwordAttrValue = user.getObjectAttribute(passwordAttributeName);
        return passwordEncoder.isPasswordValid(new String((byte[]) passwordAttrValue),
                password, null);
    }

    private boolean isLdapPasswordCompare(DirContextOperations user,
                                          SpringSecurityLdapTemplate ldapTemplate, String password) {
        String encodedPassword = passwordEncoder.encodePassword(password, null);
        byte[] passwordBytes = Utf8.encode(encodedPassword);
        return ldapTemplate.compare(user.getDn().toString(), passwordAttributeName,
                passwordBytes);
    }

    public void setPasswordAttributeName(String passwordAttribute) {
        Assert.hasLength(passwordAttribute,
                "passwordAttributeName must not be empty or null");
        this.passwordAttributeName = passwordAttribute;
    }

    private void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        Assert.notNull(passwordEncoder, "passwordEncoder must not be null.");
        this.passwordEncoder = passwordEncoder;
    }

    public void setPasswordEncoder(Object passwordEncoder) {
        if (passwordEncoder instanceof PasswordEncoder) {
            this.usePasswordAttrCompare = false;
            setPasswordEncoder((PasswordEncoder) passwordEncoder);
            return;
        }

        if (passwordEncoder instanceof org.springframework.security.crypto.password.PasswordEncoder) {
            final org.springframework.security.crypto.password.PasswordEncoder delegate = (org.springframework.security.crypto.password.PasswordEncoder) passwordEncoder;
            setPasswordEncoder(new PasswordEncoder() {
                public String encodePassword(String rawPass, Object salt) {
                    checkSalt(salt);
                    return delegate.encode(rawPass);
                }

                public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
                    checkSalt(salt);
                    return delegate.matches(rawPass, encPass);
                }

                private void checkSalt(Object salt) {
                    Assert.isNull(salt,
                            "Salt value must be null when used with crypto module PasswordEncoder");
                }
            });
            this.usePasswordAttrCompare = true;
            return;
        }

        throw new IllegalArgumentException(
                "passwordEncoder must be a PasswordEncoder instance");
    }
}
