package com.example.projectuas_petshop.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    //Bikin database dan hosting domain nya
    //private static final String BASE_URL = "https://bibsky.my.id/projectUAS-petshop/";
    private static final String BASE_URL = "http://10.0.30.2/projectUAS-petshop/";

    private static Retrofit retrofit;

    public static Retrofit getClient() {

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;

    }
}
