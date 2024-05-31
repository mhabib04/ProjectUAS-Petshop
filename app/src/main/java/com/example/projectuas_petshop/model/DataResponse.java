package com.example.projectuas_petshop.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DataResponse {

	@SerializedName("data")
	private List<DataItem> data;

	public List<DataItem> getData(){
		return data;
	}
}