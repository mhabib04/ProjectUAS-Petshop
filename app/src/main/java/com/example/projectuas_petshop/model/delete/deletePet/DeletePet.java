package com.example.projectuas_petshop.model.delete.deletePet;

import com.google.gson.annotations.SerializedName;

import com.google.gson.annotations.SerializedName;

public class DeletePet {

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
