package com.example.projectuas_petshop.model.login;

import com.google.gson.annotations.SerializedName;

public class LoginData {

	@SerializedName("id_user")
	private String idUser;

	@SerializedName("name")
	private String name;

	@SerializedName("username")
	private String username;
	@SerializedName("role")
	private String role;
	@SerializedName("image")
	private String image;

	public void setIdUser(String idUser){
		this.idUser = idUser;
	}

	public String getIdUser(){
		return idUser;
	}

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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}