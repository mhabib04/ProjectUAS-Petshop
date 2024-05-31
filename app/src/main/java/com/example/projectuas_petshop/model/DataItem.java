package com.example.projectuas_petshop.model;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("price")
	private int price;

	@SerializedName("id_animal")
	private int idAnimal;

	@SerializedName("type")
	private String type;

	@SerializedName("breed")
	private String breed;

	@SerializedName("age")
	private int age;

	public int getPrice(){
		return price;
	}

	public int getIdAnimal(){
		return idAnimal;
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