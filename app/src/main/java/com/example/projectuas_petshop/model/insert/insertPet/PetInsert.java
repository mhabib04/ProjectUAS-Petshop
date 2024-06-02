package com.example.projectuas_petshop.model.insert.insertPet;

import com.google.gson.annotations.SerializedName;

public class PetInsert {

	@SerializedName("data")
	private PetDataInsert petDataInsert;

	@SerializedName("message")
	private String message;

	public void setDataAnimal(PetDataInsert petDataInsert) {
		this.petDataInsert = petDataInsert;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@SerializedName("status")
	private boolean status;

	public PetDataInsert getData(){
		return petDataInsert;
	}

	public String getMessage(){
		return message;
	}

	public boolean isStatus(){
		return status;
	}
}