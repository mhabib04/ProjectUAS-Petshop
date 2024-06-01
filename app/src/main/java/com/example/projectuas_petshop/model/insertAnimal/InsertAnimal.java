package com.example.projectuas_petshop.model.insertAnimal;

import com.google.gson.annotations.SerializedName;

public class InsertAnimal{

	@SerializedName("data")
	private DataAnimal dataAnimal;

	@SerializedName("message")
	private String message;

	public void setDataAnimal(DataAnimal dataAnimal) {
		this.dataAnimal = dataAnimal;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@SerializedName("status")
	private boolean status;

	public DataAnimal getData(){
		return dataAnimal;
	}

	public String getMessage(){
		return message;
	}

	public boolean isStatus(){
		return status;
	}
}