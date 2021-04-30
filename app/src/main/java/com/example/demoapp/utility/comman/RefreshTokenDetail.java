package com.example.demoapp.utility.comman;


import java.io.Serializable;

public class RefreshTokenDetail implements Serializable {
    public String access_token="";
    public String token_type="";
    public String refresh_token="";
    public Integer expires_in;

}
