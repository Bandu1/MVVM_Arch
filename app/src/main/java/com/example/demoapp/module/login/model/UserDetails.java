package com.example.demoapp.module.login.model;
import java.io.Serializable;
public class UserDetails implements Serializable {
    public Integer user_id;
    public String name = "";
    public String mobileNo = "";
    public String email = "";
    public Integer gender;
    public String genderName = "";
    public String profile_image = "";
    public String imagepath = "";
    public boolean is_member = false;
    public boolean is_visitor = false;
    public boolean is_otpvarified = false;
    public boolean is_adharverified = false;
    public UserToken token;
}
