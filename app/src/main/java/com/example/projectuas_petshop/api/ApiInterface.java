package com.example.projectuas_petshop.api;

import com.example.projectuas_petshop.model.delete.deleteFood.DeleteFood;
import com.example.projectuas_petshop.model.delete.deletePet.DeletePet;
import com.example.projectuas_petshop.model.insert.insertFood.FoodInsert;
import com.example.projectuas_petshop.model.insert.insertPet.PetInsert;
import com.example.projectuas_petshop.model.select.getFood.GetFood;
import com.example.projectuas_petshop.model.select.getPet.GetPet;
import com.example.projectuas_petshop.model.select.selectFood.FoodSelect;
import com.example.projectuas_petshop.model.select.selectFoodByType.FoodSelectByType;
import com.example.projectuas_petshop.model.select.selectPet.PetSelect;
import com.example.projectuas_petshop.model.login.Login;
import com.example.projectuas_petshop.model.register.Register;
import com.example.projectuas_petshop.model.select.selectPetByType.PetSelectByType;
import com.example.projectuas_petshop.model.transaction.Transaction;
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

    @Multipart
    @POST("insertPet.php")
    Call<PetInsert> insertPetResponse(
            @Part("type") RequestBody type,
            @Part("breed") RequestBody breed,
            @Part("price") RequestBody price,
            @Part("age") RequestBody age,
            @Part MultipartBody.Part image
    );
    @Multipart
    @POST("insertFood.php")
    Call<FoodInsert> insertFoodResponse(
            @Part("type") RequestBody type,
            @Part("name") RequestBody name,
            @Part("price") RequestBody price,
            @Part MultipartBody.Part image
    );

    @FormUrlEncoded
    @POST("deletePet.php")
    Call<DeletePet> deletePet(@Field("id_pet") int id_pet);
    @FormUrlEncoded
    @POST("deleteFood.php")
    Call<DeleteFood> deleteFood(@Field("id_food") int id_food);

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
    @POST("transaction.php")
    Call<Transaction> transaction(
            @Field("id_user") int id_user,
            @Field("id_pet") Integer id_pet,
            @Field("id_food") Integer id_food,
            @Field("transaction_date") String transactionDate
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
}
