package com.ttis.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by tap on 6/18/16.
 */
@ConfigurationProperties(prefix = "ttis.security", ignoreUnknownFields = true)
public class TTISSecurityProperties {
    private String userFile = "classpath:userDetails.json";
    private String rolePrefix = "ROLE.";
    private String permissionPrefix = "TTIS.";
    private boolean localUserRepo;

    public boolean isLocalUserRepo() {
        return localUserRepo;
    }

    public void setLocalUserRepo(boolean localUserRepo) {
        this.localUserRepo = localUserRepo;
    }

    public String getPermissionPrefix() {
        return permissionPrefix;
    }

    public void setPermissionPrefix(String permissionPrefix) {
        this.permissionPrefix = permissionPrefix;
    }

    public String getRolePrefix() {
        return rolePrefix;
    }

    public void setRolePrefix(String rolePrefix) {
        this.rolePrefix = rolePrefix;
    }

    public String getUserFile() {
        return userFile;
    }

    public void setUserFile(String userFile) {
        this.userFile = userFile;
    }
}
