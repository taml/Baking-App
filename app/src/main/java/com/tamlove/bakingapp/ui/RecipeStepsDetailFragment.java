package com.tamlove.bakingapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tamlove.bakingapp.R;
import com.tamlove.bakingapp.models.Steps;

import java.util.ArrayList;

public class RecipeStepsDetailFragment extends Fragment {

    private static final String STEPS_PARCELABLE_KEY = "steps_parcelable";
    private static final String STEPS_ID_PARCELABLE_KEY = "steps_id_parcelable";

    private TextView mDetailVideoUrl;
    private TextView mShortDescription;
    private TextView mDescription;
    private Button mPrevStep;
    private Button mNextStep;
    private static ArrayList<Steps> sSteps;
    private static int sPosition;

    public RecipeStepsDetailFragment(){}

    public static RecipeStepsDetailFragment newInstance(ArrayList<Steps> steps, int stepId){
        RecipeStepsDetailFragment recipeStepsDetailFragment = new RecipeStepsDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(STEPS_PARCELABLE_KEY, steps);
        bundle.putInt(STEPS_ID_PARCELABLE_KEY, stepId);
        recipeStepsDetailFragment.setArguments(bundle);
        return recipeStepsDetailFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            sSteps = getArguments().getParcelableArrayList(STEPS_PARCELABLE_KEY);
            sPosition = getArguments().getInt(STEPS_ID_PARCELABLE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe_steps_detail, container, false);

        mDetailVideoUrl = rootView.findViewById(R.id.recipe_detail_step_url);
        mShortDescription = rootView.findViewById(R.id.recipe_detail_short_description);
        mDescription = rootView.findViewById(R.id.recipe_detail_description);
        mPrevStep = rootView.findViewById(R.id.prev_step);
        mNextStep = rootView.findViewById(R.id.next_step);

        Steps currentStep = sSteps.get(sPosition);
        String videoUrl = currentStep.getVideoURL();
        mDetailVideoUrl.setText(videoUrl);
        String shortDescription = currentStep.getShortDescription();
        mShortDescription.setText(shortDescription);
        String description = currentStep.getDescription();
        mDescription.setText(description);

        if(sPosition == 0){
            mPrevStep.setVisibility(View.INVISIBLE);
        }

        mPrevStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sPosition > 0) {
                    Steps prevStep = sSteps.get(--sPosition);
                    String videoUrl = prevStep.getVideoURL();
                    mDetailVideoUrl.setText(videoUrl);
                    String shortDescription = prevStep.getShortDescription();
                    mShortDescription.setText(shortDescription);
                    String description = prevStep.getDescription();
                    mDescription.setText(description);
                }
                if(sPosition < (sSteps.size() - 1)){
                    mNextStep.setVisibility(View.VISIBLE);
                }
                if(sPosition == 0){
                    mPrevStep.setVisibility(View.INVISIBLE);
                }
            }
        });

        if(sPosition == (sSteps.size() - 1)){
            mNextStep.setVisibility(View.INVISIBLE);
        }

        mNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sPosition < (sSteps.size() - 1)){
                    Steps nextStep = sSteps.get(++sPosition);
                    String videoUrl = nextStep.getVideoURL();
                    mDetailVideoUrl.setText(videoUrl);
                    String shortDescription = nextStep.getShortDescription();
                    mShortDescription.setText(shortDescription);
                    String description = nextStep.getDescription();
                    mDescription.setText(description);
                }
                if(sPosition > 0){
                    mPrevStep.setVisibility(View.VISIBLE);
                }
                if(sPosition == (sSteps.size() - 1)){
                    mNextStep.setVisibility(View.INVISIBLE);
                }
            }
        });

        return rootView;
    }
}
