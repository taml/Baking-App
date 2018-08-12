package com.tamlove.bakingapp.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.squareup.picasso.Picasso;
import com.tamlove.bakingapp.R;
import com.tamlove.bakingapp.models.Steps;

import java.util.ArrayList;

public class RecipeStepsDetailFragment extends Fragment implements Player.EventListener {

    private static final String STEPS_PARCELABLE_KEY = "steps_parcelable";
    private static final String STEPS_ID_PARCELABLE_KEY = "steps_id_parcelable";
    private static final String STEP_POSITION_KEY = "step_position";

    private ImageView mDetailImage;
    private TextView mDescription;
    private Button mPrevStep;
    private Button mNextStep;
    private static ArrayList<Steps> sSteps;
    private static int sPosition;
    private String mVideoUrl = "";
    PlayerView mPlayerView;
    private SimpleExoPlayer mSimpleExoPlayer;
    private boolean mPlayWhenReady = true;
    long mPlaybackPosition;
    int mCurrentWindow;

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
        if(savedInstanceState != null){
            sPosition = savedInstanceState.getInt(STEP_POSITION_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe_steps_detail, container, false);

        mPlayerView = rootView.findViewById(R.id.recipe_detail_step_video);
        mDetailImage = rootView.findViewById(R.id.recipe_detail_step_image);
        mDescription = rootView.findViewById(R.id.recipe_detail_description);
        mPrevStep = rootView.findViewById(R.id.prev_step);
        mNextStep = rootView.findViewById(R.id.next_step);

        Steps currentStep = sSteps.get(sPosition);
        mVideoUrl = currentStep.getVideoURL();
        String imageUrl = currentStep.getThumbnailURL();
        setRecipeImage(mVideoUrl, imageUrl);

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
                    mVideoUrl = prevStep.getVideoURL();
                    String imageUrl = prevStep.getThumbnailURL();
                    setRecipeImage(mVideoUrl, imageUrl);
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
                    mVideoUrl = nextStep.getVideoURL();
                    String imageUrl = nextStep.getThumbnailURL();
                    setRecipeImage(mVideoUrl, imageUrl);
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STEP_POSITION_KEY, sPosition);
    }

    private void setRecipeImage(String videoUrl, String imageUrl){
        if(TextUtils.isEmpty(videoUrl)){
            mDetailImage.setVisibility(View.VISIBLE);
            if(!TextUtils.isEmpty(imageUrl)){
                Picasso.get()
                        .load(imageUrl)
                        .placeholder(R.drawable.baking_background)
                        .error(R.drawable.baking_background)
                        .into(mDetailImage);
            } else {
                mDetailImage.setImageResource(R.drawable.baking_background);
            }
        } else {
            mDetailImage.setVisibility(View.INVISIBLE);
        }
    }

    private void initialisePlayer(String videoUrl) {
        if(!TextUtils.isEmpty(videoUrl)) {
            if(mSimpleExoPlayer == null) {
                mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
                        new DefaultRenderersFactory(getContext()),
                        new DefaultTrackSelector(),
                        new DefaultLoadControl());

                mPlayerView.setPlayer(mSimpleExoPlayer);
                mSimpleExoPlayer.setPlayWhenReady(mPlayWhenReady);
                mSimpleExoPlayer.seekTo(mCurrentWindow, mPlaybackPosition);

                Uri uri = Uri.parse(videoUrl);
                MediaSource mediaSource = buildMediaSource(uri);
                mSimpleExoPlayer.prepare(mediaSource, true, false);
            }
        }
    }

    private MediaSource buildMediaSource(Uri uri){
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("baking-app")).createMediaSource(uri);
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }

    @Override
    public void onStart() {
        super.onStart();
        initialisePlayer(mVideoUrl);
    }

    @Override
    public void onResume() {
        super.onResume();
        initialisePlayer(mVideoUrl);
    }
}
