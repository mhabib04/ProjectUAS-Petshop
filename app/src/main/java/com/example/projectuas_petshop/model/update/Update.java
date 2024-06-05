package com.example.projectuas_petshop.model.update;

import com.google.gson.annotations.SerializedName;

public class Update{

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