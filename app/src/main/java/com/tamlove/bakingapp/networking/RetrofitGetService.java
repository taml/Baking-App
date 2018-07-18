package com.tamlove.bakingapp.networking;

import java.util.List;

import com.tamlove.bakingapp.models.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitGetService {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipes();
}
