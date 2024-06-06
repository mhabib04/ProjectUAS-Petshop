package com.example.projectuas_petshop.model.insert.insertFood;

import com.google.gson.annotations.SerializedName;

public class FoodInsert {

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