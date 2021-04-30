package com.example.demoapp.module.login.model;


import java.io.Serializable;

public class UserToken implements Serializable {
    public String access_token = "";
    public String token_type = "";
    public String refresh_token = "";
    public Integer expires_in;

}
