package com.example.projectuas_petshop.model.select.getPet;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GetPet{

	@SerializedName("data")
	private List<GetPetData> data;

	@SerializedName("status")
	private boolean status;

	public List<GetPetData> getData(){
		return data;
	}

	public boolean isStatus(){
		return status;
	}
}