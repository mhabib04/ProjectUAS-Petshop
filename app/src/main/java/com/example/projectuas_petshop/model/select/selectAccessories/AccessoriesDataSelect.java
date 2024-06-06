package com.example.projectuas_petshop.model.select.selectAccessories;

import com.google.gson.annotations.SerializedName;

public class AccessoriesDataSelect {

	@SerializedName("image")
	private String image;

	@SerializedName("id_accessories")
	private int idAccessories;

	@SerializedName("price")
	private int price;

	@SerializedName("name")
	private String name;

	public String getImage(){
		return image;
	}

	public int getIdAccessories(){
		return idAccessories;
	}

	public int getPrice(){
		return price;
	}

	public String getName(){
		return name;
	}
}