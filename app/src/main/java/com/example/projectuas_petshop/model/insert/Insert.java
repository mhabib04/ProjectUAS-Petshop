package com.example.projectuas_petshop.model.insert;

import com.google.gson.annotations.SerializedName;

public class Insert {
    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;


    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    public String getMessage(){
        return message;
    }

    public boolean isStatus(){
        return status;
    }
}
