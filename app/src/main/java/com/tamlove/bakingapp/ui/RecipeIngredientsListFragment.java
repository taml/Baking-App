package com.tamlove.bakingapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tamlove.bakingapp.R;
import com.tamlove.bakingapp.adapters.RecipeIngredientsListAdapter;
import com.tamlove.bakingapp.models.Ingredients;
import com.tamlove.bakingapp.models.Recipe;

import java.util.List;

public class RecipeIngredientsListFragment extends Fragment {

    private static final String RECIPE_PARCELABLE_KEY = "recipe_parcelable";

    private RecyclerView mIngredientsRecyclerView;
    private RecipeIngredientsListAdapter mIngredientsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static Recipe sRecipe;
    private List<Ingredients> mIngredients;

    public RecipeIngredientsListFragment(){}

    public static RecipeIngredientsListFragment newInstance(Recipe recipe){
        RecipeIngredientsListFragment recipeIngredientsListFragment = new RecipeIngredientsListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(RECIPE_PARCELABLE_KEY, recipe);
        recipeIngredientsListFragment.setArguments(bundle);
        return recipeIngredientsListFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            sRecipe = getArguments().getParcelable(RECIPE_PARCELABLE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe_ingredients_list, container, false);

        mIngredientsRecyclerView = rootView.findViewById(R.id.ingredients_recycler_view);
        layoutManager = new LinearLayoutManager(getContext());
        mIngredientsRecyclerView.setLayoutManager(layoutManager);
        mIngredientsRecyclerView.setHasFixedSize(true);

        if(sRecipe != null){
            mIngredients = sRecipe.getIngredients();
            mIngredientsAdapter = new RecipeIngredientsListAdapter(getContext(), mIngredients);
            mIngredientsRecyclerView.setAdapter(mIngredientsAdapter);
        }

        return rootView;
    }
}
