package com.example.projectuas_petshop.model.select.selectFood;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class FoodSelect {

	@SerializedName("data")
	private List<FoodDataSelect> data;

	public List<FoodDataSelect> getData(){
		return data;
	}
}