package com.redridgeapps.baking.ui.detail.step_detail;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.redridgeapps.baking.R;
import com.redridgeapps.baking.databinding.FragmentStepDetailBinding;
import com.redridgeapps.baking.model.Step;
import com.redridgeapps.baking.ui.base.BaseFragment;

public class StepDetailFragment extends BaseFragment<FragmentStepDetailBinding> {

    private static final String ARG_STEP = "step";
    private static final String ARG_LOCATION = "location";

    private Step step;
    private Location location;

    private SimpleExoPlayer player;
    private boolean playWhenReady;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    private FragmentInteractionListener activityListener;

    public static StepDetailFragment newInstance(Step step, Location location) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_STEP, step);
        args.putSerializable(ARG_LOCATION, location);

        StepDetailFragment fragment = new StepDetailFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            step = getArguments().getParcelable(ARG_STEP);
            location = (Location) getArguments().getSerializable(ARG_LOCATION);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        setupLayout();

        return rootView;
    }

    @Override
    protected int provideLayout() {
        return R.layout.fragment_step_detail;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) initializePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        //hideSystemUi();
        if (Util.SDK_INT <= 23 || player == null) initializePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) releasePlayer();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteractionListener) {
            activityListener = (FragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activityListener = null;
    }

    private void setupLayout() {
        getBinding().setStep(step);

        if (location == Location.FIRST)
            getBinding().previousStep.setVisibility(View.GONE);
        else if (location == Location.LAST)
            getBinding().nextStep.setVisibility(View.GONE);

        getBinding().previousStep.setOnClickListener(view -> activityListener.onPreviousStep());
        getBinding().nextStep.setOnClickListener(view -> activityListener.onNextStep());
    }

    private void initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(requireContext()),
                new DefaultTrackSelector(),
                new DefaultLoadControl()
        );

        getBinding().exoplayer.setPlayer(player);

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);

        Uri uri = Uri.parse(step.getVideoURL());
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource
                .Factory(new DefaultHttpDataSourceFactory(requireContext().getPackageName()))
                .createMediaSource(uri);
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    public interface FragmentInteractionListener {

        void onPreviousStep();

        void onNextStep();
    }

    public enum Location {
        FIRST,
        MIDDLE,
        LAST
    }
}
