package com.example.projectuas_petshop.model.select.getFood;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GetFood{

	@SerializedName("data")
	private List<GetFoodData> data;

	@SerializedName("status")
	private boolean status;

	public List<GetFoodData> getData(){
		return data;
	}

	public boolean isStatus(){
		return status;
	}
}