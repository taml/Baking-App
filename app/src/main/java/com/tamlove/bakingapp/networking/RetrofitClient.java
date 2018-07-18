package com.tamlove.bakingapp.networking;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;
    private static final String RECIPE_BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";

    public static Retrofit getClientInstance(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(RECIPE_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
