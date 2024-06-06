package com.example.projectuas_petshop.model.delete.deleteAccessories;

import com.google.gson.annotations.SerializedName;

public class DeleteAccessories {
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
