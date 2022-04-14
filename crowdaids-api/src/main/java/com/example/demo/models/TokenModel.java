package com.example.demo.models;

public class TokenModel {

    private String token;

    public TokenModel(String tokenJwt) {
        this.token = tokenJwt;
    }

    public String getToken () { return token; }

    public void setToken (String token) { this.token = token; }
}
