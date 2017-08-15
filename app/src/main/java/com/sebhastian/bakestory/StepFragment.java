package com.sebhastian.bakestory;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.sebhastian.bakestory.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Yonathan Sebhastian on 8/14/2017.
 */

public class StepFragment extends Fragment {
    private final String LOG_TAG = StepFragment.class.getSimpleName();

    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;
    private BandwidthMeter bandwidthMeter;
    private Handler mainHandler;
    private String videoURL;

    public Step mStep;
    @BindView(R.id.recipe_step_video)
    SimpleExoPlayerView recipeStepVideoView;
    @BindView(R.id.recipe_step_description)
    TextView recipeStepDescriptionView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, rootView);

        mStep = getActivity().getIntent().getExtras().getParcelable("STEP_OBJ");
        if (mStep != null){
            recipeStepDescriptionView.setText(mStep.getDescription());
            simpleExoPlayerView = recipeStepVideoView;
            simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
            videoURL = mStep.getVideoURL();
            if (!videoURL.isEmpty()) {
                initializePlayer(Uri.parse(videoURL));
            }
            else{
                player=null;
                simpleExoPlayerView.setVisibility(View.GONE);
            }
        }

        else {
            mStep = getArguments().getParcelable("STEP_OBJ");

            if (mStep != null) {
                recipeStepDescriptionView.setText(mStep.getDescription());
                simpleExoPlayerView = recipeStepVideoView;
                simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT);
                videoURL = mStep.getVideoURL();
                if (!videoURL.isEmpty()) {
                    initializePlayer(Uri.parse(videoURL));
                }
                else{
                    player=null;
                    simpleExoPlayerView.setVisibility(View.GONE);
                }
            }

        }
        return rootView;
    }

    private void initializePlayer(Uri mediaUri) {
        Context context = getActivity();
        if (player == null) {
            mainHandler = new Handler();
            bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);

            player =ExoPlayerFactory.newSimpleInstance(context, trackSelector);
            simpleExoPlayerView.setPlayer(player);
            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                    Util.getUserAgent(context, "Bake Story"), bandwidthMeter);

            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            MediaSource videoSource = new ExtractorMediaSource(mediaUri,
                    dataSourceFactory, extractorsFactory, null, null);

            player.prepare(videoSource);
            player.setPlayWhenReady(true);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (player!=null) {
            player.stop();
            player.release();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (player!=null) {
            player.stop();
            player.release();
            player=null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (player!=null) {
            player.stop();
            player.release();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player!=null) {
            player.stop();
            player.release();
        }
    }
}
