package com.example.projectuas_petshop.model.select.getAccessories;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GetAccessories {

	@SerializedName("data")
	private List<GetAccessoriesData> data;

	@SerializedName("status")
	private boolean status;

	public List<GetAccessoriesData> getData(){
		return data;
	}

	public boolean isStatus(){
		return status;
	}
}