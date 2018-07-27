package com.tamlove.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tamlove.bakingapp.R;
import com.tamlove.bakingapp.models.Ingredients;

import java.util.List;

public class RecipeIngredientsListAdapter extends RecyclerView.Adapter<RecipeIngredientsListAdapter.RecipeIngredientsViewHolder> {

    private Context mContext;
    private List<Ingredients> mIngredients;

    public RecipeIngredientsListAdapter(Context context, List<Ingredients> ingredients){
        mContext = context;
        mIngredients = ingredients;
    }

    public class RecipeIngredientsViewHolder extends RecyclerView.ViewHolder {

        public final TextView mQuantity;
        public final TextView mMeasure;
        public final TextView mIngredient;

        public RecipeIngredientsViewHolder(View view) {
            super(view);
            mQuantity = view.findViewById(R.id.recipe_quantity);
            mMeasure = view.findViewById(R.id.recipe_measure);
            mIngredient = view.findViewById(R.id.recipe_ingredient);
        }
    }

    @Override
    public RecipeIngredientsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int idForListItem = R.layout.recipe_ingredients_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean attachToParentImmediately = false;

        View view = layoutInflater.inflate(idForListItem, viewGroup, attachToParentImmediately);
        return new RecipeIngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeIngredientsViewHolder holder, int position) {
        Ingredients ingredients = mIngredients.get(position);
        holder.mQuantity.setText(ingredients.getQuantity());
        holder.mMeasure.setText(ingredients.getMeasure());
        holder.mIngredient.setText(ingredients.getIngredient());
    }

    @Override
    public int getItemCount() {
        if(mIngredients == null) return 0;
        return mIngredients.size();
    }
}
