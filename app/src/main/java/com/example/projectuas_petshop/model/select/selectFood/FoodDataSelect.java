package com.example.projectuas_petshop.model.select.selectFood;

import com.google.gson.annotations.SerializedName;

public class FoodDataSelect {

	@SerializedName("price")
	private int price;

	@SerializedName("name")
	private String name;

	@SerializedName("type")
	private String type;

	@SerializedName("id_food")
	private int idFood;

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