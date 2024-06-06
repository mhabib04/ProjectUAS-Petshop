package com.example.projectuas_petshop.model.insert.insertAccessories;

import com.google.gson.annotations.SerializedName;

public class AccessoriesInsert {
    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;


    public String getMessage(){
        return message;
    }

    public boolean isStatus(){
        return status;
    }
}
