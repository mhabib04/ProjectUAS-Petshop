package com.example.projectuas_petshop.model.select.selectFood;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class FoodSelect {

	@SerializedName("data")
	private List<FoodDataSelect> data;

	public List<FoodDataSelect> getData(){
		return data;
	}

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;
	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}
}