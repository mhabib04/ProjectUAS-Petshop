package com.example.projectuas_petshop.model.delete;

import com.google.gson.annotations.SerializedName;

public class Delete {
    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
