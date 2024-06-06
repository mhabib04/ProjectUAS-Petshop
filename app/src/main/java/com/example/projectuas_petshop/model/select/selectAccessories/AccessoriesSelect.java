package com.example.projectuas_petshop.model.select.selectAccessories;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AccessoriesSelect{

	@SerializedName("data")
	private List<AccessoriesDataSelect> data;

	@SerializedName("status")
	private boolean status;

	public List<AccessoriesDataSelect> getData(){
		return data;
	}

	public boolean isStatus(){
		return status;
	}
}