package com.example.projectuas_petshop.api;

import com.example.projectuas_petshop.model.insertFood.FoodInsert;
import com.example.projectuas_petshop.model.insertPet.PetInsert;
import com.example.projectuas_petshop.model.selectFood.FoodSelect;
import com.example.projectuas_petshop.model.selectPet.PetSelect;
import com.example.projectuas_petshop.model.login.Login;
import com.example.projectuas_petshop.model.register.Register;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login.php")
    Call<Login> loginResponse(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register.php")
    Call<Register> registerResponse(
            @Field("username") String username,
            @Field("password") String password,
            @Field("name") String name,
            @Field("role") String role
    );

    @GET("selectPet.php")
    Call<PetSelect> getPetData();
    @GET("selectFood.php")
    Call<FoodSelect> getFoodData();

    @FormUrlEncoded
    @POST("insertPet.php")
    Call<PetInsert> insertPetResponse(
            @Field("type") String type,
            @Field("breed") String breed,
            @Field("price") int price,
            @Field("age") int age
    );
    @FormUrlEncoded
    @POST("insertFood.php")
    Call<FoodInsert> insertFoodResponse(
            @Field("type") String type,
            @Field("name") String name,
            @Field("price") int price
    );

}
