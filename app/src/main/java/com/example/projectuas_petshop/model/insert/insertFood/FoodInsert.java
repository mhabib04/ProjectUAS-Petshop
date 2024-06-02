package com.example.projectuas_petshop.model.insert.insertFood;

import com.google.gson.annotations.SerializedName;

public class FoodInsert {

	@SerializedName("data")
	private FootDataInsert footDataInsert;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public FootDataInsert getData(){
		return footDataInsert;
	}

	public String getMessage(){
		return message;
	}

	public boolean isStatus(){
		return status;
	}
}