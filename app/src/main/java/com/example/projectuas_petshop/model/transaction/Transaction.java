package com.example.projectuas_petshop.model.transaction;

import com.google.gson.annotations.SerializedName;

public class Transaction{

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