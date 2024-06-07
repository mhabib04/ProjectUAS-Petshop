package com.example.projectuas_petshop.api;

import com.example.projectuas_petshop.model.delete.Delete;
import com.example.projectuas_petshop.model.insert.Insert;
import com.example.projectuas_petshop.model.select.getAccessories.GetAccessories;
import com.example.projectuas_petshop.model.select.getFood.GetFood;
import com.example.projectuas_petshop.model.select.getPet.GetPet;
import com.example.projectuas_petshop.model.select.selectAccessories.AccessoriesSelect;
import com.example.projectuas_petshop.model.select.selectFood.FoodSelect;
import com.example.projectuas_petshop.model.select.selectFoodByType.FoodSelectByType;
import com.example.projectuas_petshop.model.select.selectPet.PetSelect;
import com.example.projectuas_petshop.model.login.Login;
import com.example.projectuas_petshop.model.register.Register;
import com.example.projectuas_petshop.model.select.selectPetByType.PetSelectByType;
import com.example.projectuas_petshop.model.update.Update;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login.php")
    Call<Login> loginResponse(
            @Field("username") String username,
            @Field("password") String password
    );

    @Multipart
    @POST("register.php")
    Call<Register> registerResponse(
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("name") RequestBody name,
            @Part("role") RequestBody role,
            @Part MultipartBody.Part image
    );

    @GET("selectPet.php")
    Call<PetSelect> getPetData();

    @GET("selectFood.php")
    Call<FoodSelect> getFoodData();

    @GET("selectAccessories.php")
    Call<AccessoriesSelect> getAccessoriesData();

    @Multipart
    @POST("insertPet.php")
    Call<Insert> insertPetResponse(
            @Part("type") RequestBody type,
            @Part("breed") RequestBody breed,
            @Part("price") RequestBody price,
            @Part("age") RequestBody age,
            @Part MultipartBody.Part image
    );

    @Multipart
    @POST("insertFood.php")
    Call<Insert> insertFoodResponse(
            @Part("type") RequestBody type,
            @Part("name") RequestBody name,
            @Part("price") RequestBody price,
            @Part MultipartBody.Part image
    );

    @Multipart
    @POST("insertAccessories.php")
    Call<Insert> insertAccessoriesResponse(
            @Part("name") RequestBody name,
            @Part("price") RequestBody price,
            @Part MultipartBody.Part image
    );

    @FormUrlEncoded
    @POST("deletePet.php")
    Call<Delete> deletePet(@Field("id_pet") int id_pet);

    @FormUrlEncoded
    @POST("deleteFood.php")
    Call<Delete> deleteFood(@Field("id_food") int id_food);

    @FormUrlEncoded
    @POST("deleteAccessories.php")
    Call<Delete> deleteAccessories(@Field("id_accessories") int id_accessories);

    @FormUrlEncoded
    @POST("selectPetByType.php")
    Call<PetSelectByType> petSelectByType(
            @Field("type") String type
    );

    @FormUrlEncoded
    @POST("selectFoodByType.php")
    Call<FoodSelectByType> foodSelectByType(
            @Field("type") String type
    );

    @FormUrlEncoded
    @POST("getPet.php")
    Call<GetPet> getPet(
            @Field("id_pet") int id_pet
    );

    @FormUrlEncoded
    @POST("getFood.php")
    Call<GetFood> getFood(
            @Field("id_food") int id_food
    );

    @FormUrlEncoded
    @POST("getAccessories.php")
    Call<GetAccessories> getAccessories(
            @Field("id_accessories") int id_accessories
    );

    @Multipart
    @POST("updatePet.php")
    Call<Update> updatePet(
            @Part("id_pet") RequestBody idPet,
            @Part("type") RequestBody type,
            @Part("breed") RequestBody breed,
            @Part("price") RequestBody price,
            @Part("age") RequestBody age,
            @Part MultipartBody.Part image
    );

    @Multipart
    @POST("updateFood.php")
    Call<Update> updateFood(
            @Part("id_food") RequestBody idFood,
            @Part("type") RequestBody type,
            @Part("name") RequestBody name,
            @Part("price") RequestBody price,
            @Part MultipartBody.Part image
    );

    @Multipart
    @POST("updateAccessories.php")
    Call<Update> updateAccessories(
            @Part("id_accessories") RequestBody idAccessories,
            @Part("name") RequestBody name,
            @Part("price") RequestBody price,
            @Part MultipartBody.Part image
    );

}
