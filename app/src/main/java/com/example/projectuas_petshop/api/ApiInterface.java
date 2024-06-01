package com.example.projectuas_petshop.api;

import com.example.projectuas_petshop.model.DataResponse;
import com.example.projectuas_petshop.model.insertAnimal.InsertAnimal;
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

    @GET("tampil_data.php")
    Call<DataResponse> getData();

    @FormUrlEncoded
    @POST("insertAnimal.php")
    Call<InsertAnimal> insertAnimalResponse(
            @Field("type") String type,
            @Field("breed") String breed,
            @Field("price") int price,
            @Field("age") int age
    );

}
