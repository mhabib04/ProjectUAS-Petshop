package com.example.projectuas_petshop.model.select.selectFoodByType;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class FoodSelectByType {

	@SerializedName("data")
	private List<FoodDataSelectByType> data;

	public List<FoodDataSelectByType> getData(){
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