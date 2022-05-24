package com.example.finalandroid.dto;

import java.io.Serializable;


public class UserCodeDto implements Serializable{
    private Integer uiId;
    private String userCode;

    public UserCodeDto() {
    }

    public UserCodeDto(Integer uiId, String userCode) {
        this.uiId = uiId;
        this.userCode = userCode;
    }

    public Integer getUiId() {
        return uiId;
    }

    public void setUiId(Integer uiId) {
        this.uiId = uiId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
}
