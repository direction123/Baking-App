package direction123.bakingapp;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import direction123.bakingapp.models.Recipe;
import direction123.bakingapp.models.Step;

public class StepDetailFragment extends Fragment {
    private static String RECIPE = "recipe";
    private static String STEP_ID = "step_id";
    private String VIDEO_PLAY_POSITION = "video_play_position";
    private Recipe mRecipe;
    private int mStepId;
    private long mCurrentPosition = 0;
    private SimpleExoPlayer mExoPlayer;
    private boolean mPlayWhenReady = true;

    @BindView(R.id.step_description) TextView mDescriptionView;
    @BindView(R.id.step_prev_button) ImageButton mPreButton;
    @BindView(R.id.step_next_button) ImageButton mNextButton;
    @BindView(R.id.step_index) TextView mStepIndexView;
    @BindView(R.id.step_video) SimpleExoPlayerView mPlayerView;


    public static StepDetailFragment newInstance(Recipe recipe, int stepId) {
        Bundle args = new Bundle();
        args.putParcelable(RECIPE, recipe);
        args.putInt(STEP_ID, stepId);

        StepDetailFragment fragment = new StepDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        ButterKnife.bind(this, rootView);

        mRecipe = getArguments().getParcelable(RECIPE);
        mStepId = getArguments().getInt(STEP_ID, 0);
        if (savedInstanceState != null) {
            mStepId = savedInstanceState.getInt("stepId");
            mCurrentPosition = savedInstanceState.getLong("currentPosition");
        }
        if(isTablet()) {
            showStepDetailTablet(mRecipe, mStepId);
        } else {
            showStepDetail(mRecipe, mStepId);
        }
        mPreButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mStepId > 0) {
                    mStepId--;
                    if(isTablet()) {
                        showStepDetailTablet(mRecipe, mStepId);
                    } else {
                        showStepDetail(mRecipe, mStepId);
                    }
                }
            }
        });
        mNextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mRecipe != null) {
                    if(mStepId < mRecipe.getSteps().size() - 1) {
                        mStepId++;
                        if(isTablet()) {
                            showStepDetailTablet(mRecipe, mStepId);
                        } else {
                            showStepDetail(mRecipe, mStepId);
                        }
                    }
                }
            }
        });

        if (mRecipe != null ) {
            Step step = mRecipe.getSteps().get(mStepId);
            if (step != null) {
                initializePlayer(Uri.parse(step.getVideoURL()), mCurrentPosition);
            }
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && !isTablet()) {
            hideSystemUi();
            int videoViewPadding = (int) getResources().getDimension(R.dimen.layout_padding_s);
            mPlayerView.getLayoutParams().height = getScreenHeight() - videoViewPadding * 3;
            mStepIndexView.setVisibility(View.GONE);
            mDescriptionView.setVisibility(View.GONE);
            mNextButton.setVisibility(View.GONE);
            mPreButton.setVisibility(View.GONE);
        }
        // Inflate the layout for this fragment
        return rootView;
    }

    private void initializePlayer(Uri mediaUri, long currentPosition) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                    getContext(),
                    new DefaultTrackSelector(),
                    new DefaultLoadControl());
            mPlayerView.setPlayer(mExoPlayer);
            mExoPlayer.setPlayWhenReady(mPlayWhenReady);
        }
        if(mediaUri != null) {
            MediaSource mediaSource = buildMediaSource(mediaUri);
            if (mExoPlayer != null && mediaSource != null) {
                mExoPlayer.prepare(mediaSource);
                mExoPlayer.seekTo(currentPosition);
            }
        }
    }

    private void releasePlayer() {
        if(mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    //reference: https://codelabs.developers.google.com/codelabs/exoplayer-intro/index.html?index=..%2F..%2Findex#2
    private void hideSystemUi() {
        mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private MediaSource buildMediaSource(Uri mediaUri) {
        String userAgent = Util.getUserAgent(getContext(), "Baking App");
        MediaSource mediaSource = new ExtractorMediaSource(mediaUri,
                new DefaultDataSourceFactory(getContext(), userAgent),
                new DefaultExtractorsFactory(), null, null);
        return mediaSource;
    }

    private void showStepDetail(Recipe recipe, int stepId) {
        if(recipe != null) {
            List<Step> stepList = recipe.getSteps();
            if(stepId == 0) {
                mPreButton.setVisibility(View.INVISIBLE);
            } else {
                mPreButton.setVisibility(View.VISIBLE);
            }
            if(stepId == stepList.size() - 1) {
                mNextButton.setVisibility(View.INVISIBLE);
            } else {
                mNextButton.setVisibility(View.VISIBLE);
            }

            Step step = recipe.getSteps().get(stepId);
            setDescriptionView(step);
            releasePlayer();
            if(step != null) {
                initializePlayer(Uri.parse(step.getVideoURL()), 0);
            }
            mStepIndexView.setText("Step " + stepId + "/" + (stepList.size() - 1));
        }
}

    private void showStepDetailTablet(Recipe recipe, int stepId) {
        if(recipe != null) {
            mPreButton.setVisibility(View.INVISIBLE);
            mNextButton.setVisibility(View.INVISIBLE);

            Step step = recipe.getSteps().get(stepId);
            setDescriptionView(step);
            releasePlayer();
            if(step != null) {
                initializePlayer(Uri.parse(step.getVideoURL()), 0);
            }
        }
    }

    private void setDescriptionView(Step step) {
        if(step != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                mDescriptionView.setText(Html.fromHtml(step.getDescription(), Html.FROM_HTML_MODE_LEGACY));
            } else {
                mDescriptionView.setText(Html.fromHtml(step.getDescription()));
            }
        }
    }

    private boolean isTablet() {
        Configuration config = getResources().getConfiguration();
        return config.smallestScreenWidthDp >= 600;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("currentPosition", mExoPlayer.getCurrentPosition());
        outState.putInt("stepId", mStepId);
    }
}
