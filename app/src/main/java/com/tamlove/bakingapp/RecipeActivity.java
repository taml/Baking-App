package com.tamlove.bakingapp;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tamlove.bakingapp.UITests.SimpleIdlingResource;
import com.tamlove.bakingapp.adapters.RecipesAdapter;
import com.tamlove.bakingapp.models.Recipe;
import com.tamlove.bakingapp.networking.RetrofitClient;
import com.tamlove.bakingapp.networking.RetrofitGetService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeActivity extends AppCompatActivity implements RecipeDelayer.DelayerCallback {

    public static final int GRID_SPAN_LARGE = 2;
    public static final int GRID_SPAN_XLARGE = 3;

    private ConnectivityManager cm;
    private NetworkInfo activeNetwork;
    private RecipesAdapter mRecipesAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TextView mErrorTextView;
    private ProgressBar mProgressBar;

    @Nullable private static SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        mErrorTextView = findViewById(R.id.error_textview);
        mProgressBar = findViewById(R.id.progress_bar);

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
        getContent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.action_refresh){
            getContent();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isConnected(){
        cm  = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    private void getContent(){
        if(isConnected()){
            RetrofitGetService service = RetrofitClient.getClientInstance().create(RetrofitGetService.class);
            Call<List<Recipe>> listCall = service.getRecipes();
            listCall.enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    if(response.code() == 200){
                        displayContent();
                        RecipeDelayer.processRecipes(response.body(), RecipeActivity.this, mIdlingResource);
                    } else {
                        displayError();
                        mErrorTextView.setText(getResources().getString(R.string.error_api_message));
                    }
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    displayError();
                    mErrorTextView.setText(getResources().getString(R.string.error_api_message));
                }
            });
        } else {
            displayError();
            mErrorTextView.setText(getResources().getString(R.string.error_connection_message));
        }
    }

    private void getDataResponse(List<Recipe> recipe){
        mRecipesAdapter = new RecipesAdapter(this, recipe);
        if(mRecipesAdapter.getItemCount() == 0) {
            displayError();
            mErrorTextView.setText(getResources().getString(R.string.error_message));
        } else {
            mRecipesAdapter.notifyDataSetChanged();
            mRecyclerView.setAdapter(mRecipesAdapter);
        }
    }

    private void displayError(){
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorTextView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    private void displayContent(){
        mProgressBar.setVisibility(View.INVISIBLE);
        mErrorTextView.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @VisibleForTesting
    @NonNull
    public static IdlingResource getIdlingResource(){
        if(mIdlingResource == null){
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    public void onDone(List<Recipe> recipeList) {
        getDataResponse(recipeList);
    }
}
