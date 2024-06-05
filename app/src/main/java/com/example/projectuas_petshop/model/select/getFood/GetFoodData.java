package com.example.projectuas_petshop.model.select.getFood;

import com.google.gson.annotations.SerializedName;

public class GetFoodData {

	@SerializedName("image")
	private String image;

	@SerializedName("price")
	private int price;

	@SerializedName("name")
	private String name;

	@SerializedName("type")
	private String type;

	@SerializedName("id_food")
	private int idFood;

	public String getImage(){
		return image;
	}

	public int getPrice(){
		return price;
	}

	public String getName(){
		return name;
	}

	public String getType(){
		return type;
	}

	public int getIdFood(){
		return idFood;
	}
}