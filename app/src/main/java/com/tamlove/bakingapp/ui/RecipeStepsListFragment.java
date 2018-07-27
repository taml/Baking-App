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
import com.tamlove.bakingapp.adapters.RecipeStepsListAdapter;
import com.tamlove.bakingapp.models.Recipe;
import com.tamlove.bakingapp.models.Steps;

import java.util.List;

public class RecipeStepsListFragment extends Fragment {

    private static final String RECIPE_PARCELABLE_KEY = "recipe_parcelable";

    private RecyclerView mStepsRecyclerView;
    private RecipeStepsListAdapter mStepsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static Recipe sRecipe;
    private List<Steps> mSteps;

    public RecipeStepsListFragment(){}

    public static RecipeStepsListFragment newInstance(Recipe recipe){
        RecipeStepsListFragment recipeStepsListFragment = new RecipeStepsListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(RECIPE_PARCELABLE_KEY, recipe);
        recipeStepsListFragment.setArguments(bundle);
        return recipeStepsListFragment;
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
        final View rootView = inflater.inflate(R.layout.fragment_recipe_steps_list, container, false);

        mStepsRecyclerView = rootView.findViewById(R.id.steps_recycler_view);
        layoutManager = new LinearLayoutManager(getContext());
        mStepsRecyclerView.setLayoutManager(layoutManager);
        mStepsRecyclerView.setHasFixedSize(true);

        if(sRecipe != null){
            mSteps = sRecipe.getSteps();
            mStepsAdapter = new RecipeStepsListAdapter(getContext(), mSteps);
            mStepsRecyclerView.setAdapter(mStepsAdapter);
        }

        return rootView;
    }
}
