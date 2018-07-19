package com.tamlove.bakingapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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

    public static final int GRID_SPAN_LARGE = 2;
    public static final int GRID_SPAN_XLARGE = 4;

    private LoaderManager loaderManager;
    private ConnectivityManager cm;
    private NetworkInfo activeNetwork;
    private RecipesAdapter mRecipesAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecipesAdapter = new RecipesAdapter(RecipeActivity.this, new ArrayList<Recipe>());
        int deviceScreenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        switch(deviceScreenSize){
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                layoutManager = new LinearLayoutManager(RecipeActivity.this);
                break;
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                layoutManager = new GridLayoutManager(RecipeActivity.this, GRID_SPAN_LARGE);
                break;
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                layoutManager = new GridLayoutManager(RecipeActivity.this, GRID_SPAN_XLARGE);
                break;
            default:
                layoutManager = new LinearLayoutManager(RecipeActivity.this);
        }
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mRecipesAdapter);

        if(isConnected()){
            RetrofitGetService service = RetrofitClient.getClientInstance().create(RetrofitGetService.class);
            Call<List<Recipe>> listCall = service.getRecipes();
            listCall.enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    if(response.code() == 200){
                        getDataResponse(response.body());
                    }
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    Log.v("Recipe Activity", "Error fetching data");
                }
            });
        }
    }

    private boolean isConnected(){
        // Get a reference to the LoaderManager to interact with loaders.
        loaderManager = getLoaderManager();
        cm  = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    private void getDataResponse(List<Recipe> recipe){
        mRecipesAdapter = new RecipesAdapter(this, recipe);
        mRecipesAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mRecipesAdapter);
    }
}
