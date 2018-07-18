package com.tamlove.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.tamlove.bakingapp.adapters.RecipesAdapter;
import com.tamlove.bakingapp.models.Recipe;
import com.tamlove.bakingapp.networking.RetrofitClient;
import com.tamlove.bakingapp.networking.RetrofitGetService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeActivity extends AppCompatActivity {

    private RecipesAdapter mRecipesAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecipesAdapter = new RecipesAdapter(RecipeActivity.this, new ArrayList<Recipe>());
        layoutManager = new LinearLayoutManager(RecipeActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mRecipesAdapter);


        RetrofitGetService service = RetrofitClient.getClientInstance().create(RetrofitGetService.class);
        Call<List<Recipe>> listCall = service.getRecipes();
        listCall.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if(response.code() == 200){
                    getDataResponse(response.body());
                }
                Log.v("Recipe Activity", "Response: " + response.code());
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.v("Recipe Activity", "Error fetching data");
            }
        });
    }

    private void getDataResponse(List<Recipe> recipe){
        mRecipesAdapter = new RecipesAdapter(this, recipe);
        mRecipesAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mRecipesAdapter);
    }
}
