package com.example.projectuas_petshop.model.select.getPet;

import com.google.gson.annotations.SerializedName;

public class GetPetData {

	@SerializedName("id_pet")
	private int idPet;

	@SerializedName("image")
	private String image;

	@SerializedName("price")
	private int price;

	@SerializedName("type")
	private String type;

	@SerializedName("age")
	private int age;

	@SerializedName("breed")
	private String breed;

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

	public int getAge(){
		return age;
	}

	public String getBreed(){
		return breed;
	}
}