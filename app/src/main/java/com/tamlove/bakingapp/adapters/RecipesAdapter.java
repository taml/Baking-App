package com.tamlove.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.tamlove.bakingapp.models.Recipe;
import com.tamlove.bakingapp.R;
import com.tamlove.bakingapp.ui.RecipeContentActivity;

import java.util.List;

import static com.tamlove.bakingapp.widget.RecipeUtils.RECIPE_PARCELABLE_KEY;
import static com.tamlove.bakingapp.widget.RecipeUtils.RECIPE_PREF;
import static com.tamlove.bakingapp.widget.RecipeUtils.RECIPE_WIDGET_PREF;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder>{

    private Context mContext;
    private List<Recipe> mRecipe;

    public RecipesAdapter(Context context, List<Recipe> recipe){
        mContext = context;
        mRecipe = recipe;
    }

    public class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView mImageView;
        public final TextView mNameTextView;
        public final TextView mServingTextView;
        public final Button mWidgetButton;

        public RecipesViewHolder(View view) {
            super(view);
            mImageView = view.findViewById(R.id.recipe_image);
            mNameTextView = view.findViewById(R.id.recipe_name);
            mServingTextView = view.findViewById(R.id.recipe_serving);
            mWidgetButton = view.findViewById(R.id.widget_button);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Recipe currentRecipe = mRecipe.get(adapterPosition);
            Bundle bundle = new Bundle();
            bundle.putParcelable(RECIPE_PARCELABLE_KEY, currentRecipe);
            Intent recipeContentIntent = new Intent(mContext, RecipeContentActivity.class);
            recipeContentIntent.putExtras(bundle);
            mContext.startActivity(recipeContentIntent);
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
        final Recipe recipe = mRecipe.get(position);
        String recipeImage = recipe.getImage();
        if(!TextUtils.isEmpty(recipeImage)){
            Picasso.get()
                    .load(recipeImage)
                    .placeholder(R.drawable.baking_background)
                    .error(R.drawable.baking_background)
                    .into(holder.mImageView);
        } else {
            holder.mImageView.setImageResource(R.drawable.baking_background);
        }
        final String name = recipe.getName();
        holder.mNameTextView.setText(name);
        String serving = recipe.getServings();
        holder.mServingTextView.setText(mContext.getResources().getString(R.string.recipe_serving, serving));
        holder.mWidgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                String recipeToGson = gson.toJson(recipe);
                SharedPreferences sharedPreferences = mContext.getSharedPreferences(RECIPE_PREF, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(RECIPE_WIDGET_PREF, recipeToGson);
                editor.apply();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mRecipe == null) return 0;
        return mRecipe.size();
    }

}
