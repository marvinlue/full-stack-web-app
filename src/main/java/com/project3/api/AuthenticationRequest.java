package com.project3.api;

import java.io.Serializable;

public class AuthenticationRequest implements Serializable {


    private String username;
    private String password;
    private Boolean rememberMe;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getRememberMe() {return rememberMe;}

    public void setRememberMe(Boolean rememberMe){this.rememberMe = rememberMe;}

    //need default constructor for JSON Parsing
    public AuthenticationRequest()
    {

    }

    public AuthenticationRequest(String username, String password, Boolean rememberMe) {
        this.setUsername(username);
        this.setPassword(password);
        this.setRememberMe(rememberMe);
    }
}