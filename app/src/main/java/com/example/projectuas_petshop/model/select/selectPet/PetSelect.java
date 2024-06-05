package com.example.projectuas_petshop.model.select.selectPet;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PetSelect {

	@SerializedName("data")
	private List<PetDataSelect> data;

	public List<PetDataSelect> getData(){
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