package com.kingthy.security;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User:pany <br>
 * Date:2016-11-3 <br>
 * Time:16:43 <br>
 * 
 */
public class LoginParameter {
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("password")
    private String password;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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
}