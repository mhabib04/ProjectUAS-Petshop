package com.example.projectuas_petshop.model.select.selectPet;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PetSelect {

	@SerializedName("data")
	private List<PetDataSelect> data;

	public List<PetDataSelect> getData(){
		return data;
	}
}