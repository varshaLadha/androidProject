package com.example.lcom151_two.retrofit;

import com.google.gson.annotations.SerializedName;

public class InsertUserResponseModel {

    @SerializedName("success")
    private int status;
    @SerializedName("message")
    private String msg;

    public InsertUserResponseModel(int status,String msg){
        this.status=status;
        this.msg=msg;
    }

    public InsertUserResponseModel(){}

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
