package com.example.projectuas_petshop.model.select.selectAccessories;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AccessoriesSelect{

	@SerializedName("data")
	private List<AccessoriesDataSelect> data;

	@SerializedName("status")
	private boolean status;
	@SerializedName("message")
	private String message;
	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}


	public List<AccessoriesDataSelect> getData(){
		return data;
	}

	public boolean isStatus(){
		return status;
	}
}