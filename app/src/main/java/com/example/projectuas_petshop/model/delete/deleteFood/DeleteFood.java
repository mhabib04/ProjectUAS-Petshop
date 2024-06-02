package com.example.projectuas_petshop.model.delete.deleteFood;

import com.google.gson.annotations.SerializedName;

public class DeleteFood {
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
