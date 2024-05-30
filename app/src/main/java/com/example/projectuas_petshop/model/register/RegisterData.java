package com.example.projectuas_petshop.model.register;

import com.google.gson.annotations.SerializedName;

public class RegisterData {

	@SerializedName("name")
	private String name;

	@SerializedName("username")
	private String username;
	@SerializedName("role")
	private String role;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}
}