package com.tamlove.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tamlove.bakingapp.R;
import com.tamlove.bakingapp.models.Steps;

import java.util.List;

public class RecipeStepsListAdapter extends RecyclerView.Adapter<RecipeStepsListAdapter.RecipeStepsViewHolder> {

    private Context mContext;
    private List<Steps> mSteps;

    public RecipeStepsListAdapter(Context context, List<Steps> steps){
        mContext = context;
        mSteps = steps;
    }

    public class RecipeStepsViewHolder extends RecyclerView.ViewHolder {

        public final TextView mShortDescription;

        public RecipeStepsViewHolder(View view) {
            super(view);
            mShortDescription = view.findViewById(R.id.recipe_short_description);
        }
    }

    @Override
    public RecipeStepsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int idForListItem = R.layout.recipe_steps_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean attachToParentImmediately = false;

        View view = layoutInflater.inflate(idForListItem, viewGroup, attachToParentImmediately);
        return new RecipeStepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeStepsViewHolder holder, int position) {
        Steps steps = mSteps.get(position);
        holder.mShortDescription.setText(steps.getShortDescription());
    }

    @Override
    public int getItemCount() {
        if(mSteps == null) return 0;
        return mSteps.size();
    }
}
