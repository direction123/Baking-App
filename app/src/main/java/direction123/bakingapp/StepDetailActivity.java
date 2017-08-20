package direction123.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import direction123.bakingapp.models.Recipe;

public class StepDetailActivity extends AppCompatActivity {
    private String RECIPE = "recipe";
    private String STEP_ID = "step_id";

    private Recipe mRecipe;
    private int mStepId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        if (savedInstanceState == null) {
            Intent intentThatStartedThisActivity = getIntent();
            if (intentThatStartedThisActivity != null) {
                if (intentThatStartedThisActivity.hasExtra(RECIPE) &&
                        intentThatStartedThisActivity.hasExtra(STEP_ID)) {
                    mRecipe = getIntent().getParcelableExtra(RECIPE);
                    mStepId = getIntent().getIntExtra(STEP_ID, 0);
                }
            }

            StepDetailFragment stepDetailFragment =
                    StepDetailFragment.newInstance(mRecipe, mStepId);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.stepDetailFragment, stepDetailFragment)
                    .commit();

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if(mRecipe != null) {
                getSupportActionBar().setTitle(mRecipe.getName());
            }
        } else {
            Recipe recipe = savedInstanceState.getParcelable(RECIPE);
            if(recipe != null) {
                getSupportActionBar().setTitle(recipe.getName());
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECIPE, mRecipe);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mRecipe = savedInstanceState.getParcelable(RECIPE);
    }
}
