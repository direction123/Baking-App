package direction123.bakingapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import direction123.bakingapp.models.Recipe;
import direction123.bakingapp.models.Step;

public class StepDetailFragment extends Fragment {
    private static String RECIPE = "recipe";
    private static String STEP_ID = "step_id";
    private Recipe mRecipe;
    private int mStepId;

    @BindView(R.id.step_description) TextView mDescriptionView;
    @BindView(R.id.step_prev_button) ImageButton mPreButton;
    @BindView(R.id.step_next_button) ImageButton mNextButton;
    @BindView(R.id.step_index) TextView mStepIndexView;

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
                    if(mStepId < mRecipe.getStepList().size() - 1) {
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



        // Inflate the layout for this fragment
        return rootView;
    }

    private void showStepDetail(Recipe recipe, int stepId) {
        if(recipe != null) {
            List<Step> stepList = recipe.getStepList();
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

            Step step = recipe.getStepList().get(stepId);
            mDescriptionView.setText(Html.escapeHtml(step.getDescription()));

            mStepIndexView.setText("Step " + stepId + "/" + (stepList.size() - 1));
        }
}

    private void showStepDetailTablet(Recipe recipe, int stepId) {
        if(recipe != null) {
            mPreButton.setVisibility(View.INVISIBLE);
            mNextButton.setVisibility(View.INVISIBLE);

            Step step = recipe.getStepList().get(stepId);
            mDescriptionView.setText(step.getDescription());
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
}
