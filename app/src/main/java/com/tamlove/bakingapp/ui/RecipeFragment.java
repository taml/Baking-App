package com.tamlove.bakingapp.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tamlove.bakingapp.R;
import com.tamlove.bakingapp.adapters.RecipesAdapter;
import com.tamlove.bakingapp.models.Recipe;
import com.tamlove.bakingapp.networking.RetrofitClient;
import com.tamlove.bakingapp.networking.RetrofitGetService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeFragment extends Fragment {

    public static final int GRID_SPAN_LARGE = 2;
    public static final int GRID_SPAN_XLARGE = 3;

    private static final String DATA_LOADER = "DATA_LOADER";

    private ConnectivityManager cm;
    private NetworkInfo activeNetwork;
    private RecipesAdapter mRecipesAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TextView mErrorTextView;
    private ProgressBar mProgressBar;


    @Nullable
    private static CountingIdlingResource mIdlingResource = new CountingIdlingResource(DATA_LOADER);
    private boolean refreshedData = false;

    public RecipeFragment(){}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        mErrorTextView = rootView.findViewById(R.id.error_textview);
        mProgressBar = rootView.findViewById(R.id.progress_bar);

        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mRecipesAdapter = new RecipesAdapter(getContext(), new ArrayList<Recipe>());
        int deviceScreenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        switch(deviceScreenSize){
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                layoutManager = new LinearLayoutManager(getContext());
                break;
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                layoutManager = new GridLayoutManager(getContext(), GRID_SPAN_LARGE);
                break;
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                layoutManager = new GridLayoutManager(getContext(), GRID_SPAN_XLARGE);
                break;
            default:
                layoutManager = new LinearLayoutManager(getContext());
        }
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mRecipesAdapter);
        mIdlingResource.increment();
        getContent();

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.action_refresh){
            refreshedData = true;
            getContent();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isConnected(){
        cm  = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
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
                        getDataResponse(response.body());
                        displayContent();
                        if(!refreshedData) {
                            mIdlingResource.decrement();
                        }
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
        mRecipesAdapter = new RecipesAdapter(getContext(), recipe);
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
    public static CountingIdlingResource getIdlingResource(){
        if(mIdlingResource == null){
            mIdlingResource = new CountingIdlingResource(DATA_LOADER);
        }
        return mIdlingResource;
    }
}
