package com.ttis.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by tap on 6/18/16.
 */
@ConfigurationProperties(prefix = "ttis.security", ignoreUnknownFields = true)
public class TTISSecurityProperties {
    private String userFile = "classpath:examplelocaluserdetails.json";
    private String rolePrefix = "ROLE.";
    private String permissionPrefix = "TTIS.";
    private String authzTokenURL;
    private String productToken;

    public String getUserFile() {
        return userFile;
    }

    public void setUserFile(String userFile) {
        this.userFile = userFile;
    }

    public String getRolePrefix() {
        return rolePrefix;
    }

    public void setRolePrefix(String rolePrefix) {
        this.rolePrefix = rolePrefix;
    }

    public String getPermissionPrefix() {
        return permissionPrefix;
    }

    public void setPermissionPrefix(String permissionPrefix) {
        this.permissionPrefix = permissionPrefix;
    }

    public String getAuthzTokenURL() {
        return authzTokenURL;
    }

    public void setAuthzTokenURL(String authzTokenURL) {
        this.authzTokenURL = authzTokenURL;
    }

    public String getProductToken() {
        return productToken;
    }

    public void setProductToken(String productToken) {
        this.productToken = productToken;
    }
}
