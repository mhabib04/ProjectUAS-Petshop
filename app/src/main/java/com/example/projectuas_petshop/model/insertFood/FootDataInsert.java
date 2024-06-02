package com.example.projectuas_petshop.model.insertFood;

import com.google.gson.annotations.SerializedName;

public class FootDataInsert {

	@SerializedName("price")
	private String price;

	@SerializedName("name")
	private String name;

	@SerializedName("type")
	private String type;

	public String getPrice(){
		return price;
	}

	public String getName(){
		return name;
	}

	public String getType(){
		return type;
	}
}