package com.example.projectuas_petshop.model.select.selectPetByType;

import com.google.gson.annotations.SerializedName;

public class PetDataSelectByType {

	@SerializedName("id_pet")
	private int idPet;

	@SerializedName("image")
	private String image;

	@SerializedName("price")
	private int price;

	@SerializedName("type")
	private String type;

	@SerializedName("breed")
	private String breed;

	@SerializedName("age")
	private int age;

	public int getIdPet(){
		return idPet;
	}

	public String getImage(){
		return image;
	}

	public int getPrice(){
		return price;
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
}