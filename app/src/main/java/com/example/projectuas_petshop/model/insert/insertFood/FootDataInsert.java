package com.example.projectuas_petshop.model.insert.insertFood;

import com.google.gson.annotations.SerializedName;

public class FootDataInsert {

	@SerializedName("price")
	private String price;

	@SerializedName("name")
	private String name;

	@SerializedName("type")
	private String type;

	@SerializedName("image")
	private String image;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

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