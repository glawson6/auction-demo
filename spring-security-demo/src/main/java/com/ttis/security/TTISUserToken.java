package com.ttis.security;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by tap on 6/22/16.
 */
public class TTISUserToken implements Serializable{

    private static final long serialVersionUID = 7431651038573621300L;
    private String email;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;

    Collection<String> permissions;
    Collection<String> roles;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Collection<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Collection<String> permissions) {
        this.permissions = permissions;
    }

    public Collection<String> getRoles() {
        return roles;
    }

    public void setRoles(Collection<String> roles) {
        this.roles = roles;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TTISUserToken that = (TTISUserToken) o;

        return userName.equals(that.userName);

    }

    @Override
    public int hashCode() {
        return userName.hashCode();
    }

}
