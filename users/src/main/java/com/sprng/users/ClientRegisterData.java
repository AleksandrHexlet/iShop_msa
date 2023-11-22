package com.sprng.users;

import java.util.Set;


public class ClientRegisterData {

    private String clientName;
    private String password;
    private String redirectURL;
//    private Set<String> redirectURL;
    private String postLogoutRedirectURL;
    private Set<String> scopes;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRedirectURL() {
        return redirectURL;
    }

    public void setRedirectURL(String redirectURL) {
        this.redirectURL = redirectURL;
    }

    public String getPostLogoutRedirectURL() {
        return postLogoutRedirectURL;
    }

    public void setPostLogoutRedirectURL(String postLogoutRedirectURL) {
        this.postLogoutRedirectURL = postLogoutRedirectURL;
    }

    public Set<String> getScopes() {
        return scopes;
    }

    public void setScopes(Set<String> scopes) {
        this.scopes = scopes;
    }
}
