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

import com.google.android.exoplayer2.C;
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
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;
import com.tamlove.bakingapp.R;
import com.tamlove.bakingapp.models.Steps;

import java.util.ArrayList;

public class RecipeStepsDetailFragment extends Fragment {

    private static final String STEPS_PARCELABLE_KEY = "steps_parcelable";
    private static final String STEPS_ID_PARCELABLE_KEY = "steps_id_parcelable";
    private static final String STEP_POSITION_KEY = "step_position";
    private static final String PLAY_WHEN_READY_KEY = "play_when_ready";
    private static final String PLAYBACK_POSITION_KEY = "playback_position";
    private static final String CURRENT_WINDOW_KEY = "current_window";

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
    boolean stateRes = false;
    boolean navClicked = false;

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
            stateRes = true;
            if(savedInstanceState.containsKey(PLAY_WHEN_READY_KEY)) {
                mPlayWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY_KEY);
            } else {
                mPlayWhenReady = true;
            }
            if(savedInstanceState.containsKey(PLAYBACK_POSITION_KEY)) {
                mPlaybackPosition = savedInstanceState.getLong(PLAYBACK_POSITION_KEY, C.TIME_UNSET);
            } else {
                mPlaybackPosition = C.TIME_UNSET;
            }
            if(savedInstanceState.containsKey(CURRENT_WINDOW_KEY)) {
                mCurrentWindow = savedInstanceState.getInt(CURRENT_WINDOW_KEY);
            } else {
                mCurrentWindow = 0;
            }
        } else {
            mPlayWhenReady = true;
            mPlaybackPosition = C.TIME_UNSET;
            mCurrentWindow = 0;
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
        setRecipeMedia(mVideoUrl, imageUrl);

        String description = currentStep.getDescription();
        mDescription.setText(description);

        if(sPosition == 0){
            mPrevStep.setVisibility(View.INVISIBLE);
        }

        mPrevStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sPosition > 0) {
                    navClicked = true;
                    Steps prevStep = sSteps.get(--sPosition);
                    mVideoUrl = prevStep.getVideoURL();
                    String imageUrl = prevStep.getThumbnailURL();
                    setRecipeMedia(mVideoUrl, imageUrl);
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
                    navClicked = true;
                    Steps nextStep = sSteps.get(++sPosition);
                    mVideoUrl = nextStep.getVideoURL();
                    String imageUrl = nextStep.getThumbnailURL();
                    setRecipeMedia(mVideoUrl, imageUrl);
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
        if(mSimpleExoPlayer != null) {
            outState.putBoolean(PLAY_WHEN_READY_KEY, mSimpleExoPlayer.getPlayWhenReady());
            outState.putLong(PLAYBACK_POSITION_KEY, mSimpleExoPlayer.getCurrentPosition());
            outState.putInt(CURRENT_WINDOW_KEY, mSimpleExoPlayer.getCurrentWindowIndex());
        }
    }

    private void setRecipeMedia(String videoUrl, String imageUrl){
        if(mSimpleExoPlayer != null) {
            releasePlayer();
        }
        if(TextUtils.isEmpty(videoUrl)){
            mPlayerView.setVisibility(View.GONE);
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
            if(mSimpleExoPlayer == null) {
                initialisePlayer(videoUrl);
            }
            mPlayerView.setVisibility(View.VISIBLE);
            mDetailImage.setVisibility(View.GONE);
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

                Uri uri = Uri.parse(videoUrl);
                MediaSource mediaSource = buildMediaSource(uri);
                if((stateRes && navClicked) || navClicked) {
                    mSimpleExoPlayer.setPlayWhenReady(mPlayWhenReady);
                    mSimpleExoPlayer.seekTo(mCurrentWindow, mPlaybackPosition);
                    mSimpleExoPlayer.prepare(mediaSource);
                    navClicked = false;
                } else {
                    mSimpleExoPlayer.prepare(mediaSource);
                    mSimpleExoPlayer.setPlayWhenReady(mPlayWhenReady);
                    mSimpleExoPlayer.seekTo(mCurrentWindow, mPlaybackPosition);
                }
                Log.v("RSDF", "*****" + stateRes + "*****" + navClicked);
            }
        }
    }

    private MediaSource buildMediaSource(Uri uri){
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("baking-app")).createMediaSource(uri);
    }

    private void releasePlayer() {
        if (mSimpleExoPlayer != null) {
            mPlaybackPosition = mSimpleExoPlayer.getCurrentPosition();
            mCurrentWindow = mSimpleExoPlayer.getCurrentWindowIndex();
            mPlayWhenReady = mSimpleExoPlayer.getPlayWhenReady();

            mSimpleExoPlayer.stop();
            mSimpleExoPlayer.release();
            mSimpleExoPlayer = null;
        }
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

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }

}
