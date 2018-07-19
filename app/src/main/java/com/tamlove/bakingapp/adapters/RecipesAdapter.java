package com.tamlove.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tamlove.bakingapp.models.Recipe;
import com.tamlove.bakingapp.R;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder>{

    private Context mContext;
    private List<Recipe> mRecipe;

    public RecipesAdapter(Context context, List<Recipe> recipe){
        mContext = context;
        mRecipe = recipe;
    }

    public class RecipesViewHolder extends RecyclerView.ViewHolder {

        public final TextView mNameTextView;

        public RecipesViewHolder(View view) {
            super(view);
            mNameTextView = view.findViewById(R.id.recipe_name);
        }
    }

    @Override
    public RecipesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int idForListItem = R.layout.recipe_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean attachToParentImmediately = false;

        View view = layoutInflater.inflate(idForListItem, viewGroup, attachToParentImmediately);
        return new RecipesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipesViewHolder holder, int position) {
        Recipe recipe = mRecipe.get(position);
        String name = recipe.getName();
        holder.mNameTextView.setText(name);
    }

    @Override
    public int getItemCount() {
        if(mRecipe == null) return 0;
        return mRecipe.size();
    }

    public void setRecipeData(List<Recipe> recipe){
        mRecipe = recipe;
        notifyDataSetChanged();
    }
}
