package com.example.demoapp.module.login.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("bStatus")
    @Expose
    private Boolean bStatus;
    @SerializedName("tMessage")
    @Expose
    private String tMessage;
    @SerializedName("iHttpCode")
    @Expose
    private Integer iHttpCode;
    @SerializedName("tData")
    @Expose
    private UserTData userTData;

    public Boolean getBStatus() {
        return bStatus;
    }

    public void setBStatus(Boolean bStatus) {
        this.bStatus = bStatus;
    }

    public String getTMessage() {
        return tMessage;
    }

    public void setTMessage(String tMessage) {
        this.tMessage = tMessage;
    }

    public Integer getIHttpCode() {
        return iHttpCode;
    }

    public void setIHttpCode(Integer iHttpCode) {
        this.iHttpCode = iHttpCode;
    }

    public UserTData getUserTData() {
        return userTData;
    }

    public void setUserTData(UserTData userTData) {
        this.userTData = userTData;
    }
}
