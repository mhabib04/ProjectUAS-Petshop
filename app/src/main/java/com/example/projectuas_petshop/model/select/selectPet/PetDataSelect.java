package com.example.projectuas_petshop.model.select.selectPet;

import com.google.gson.annotations.SerializedName;

public class PetDataSelect {

	@SerializedName("price")
	private int price;

	@SerializedName("id_pet")
	private int idPet;

	@SerializedName("type")
	private String type;

	@SerializedName("breed")
	private String breed;
	@SerializedName("image")
	private String image;

	@SerializedName("age")
	private int age;

	public int getPrice(){
		return price;
	}

	public int getIdPet(){
		return idPet;
	}

	public String getType(){
		return type;
	}

	public String getBreed(){
		return breed;
	}

	public int getAge(){
		return age;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImage() {
		return image;
	}
}