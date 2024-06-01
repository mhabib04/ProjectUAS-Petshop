package com.example.projectuas_petshop.model.insertAnimal;

import com.google.gson.annotations.SerializedName;

public class DataAnimal {

	@SerializedName("price")
	private int price;

	@SerializedName("type")
	private String type;

	@SerializedName("breed")
	private String breed;

	@SerializedName("age")
	private int age;

	public void setPrice(int price) {
		this.price = price;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	public void setAge(int age) {
		this.age = age;
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