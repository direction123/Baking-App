package direction123.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import direction123.bakingapp.models.Recipe;
import direction123.bakingapp.models.Step;

public class StepListActivity extends AppCompatActivity implements StepListFragment.OnStepClickListener{
    private Recipe mRecipe;
    private boolean mTwoPane;

    private String RECIPE = "recipe";
    private String STEP_ID = "step_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

        if (savedInstanceState == null) {
            Intent intentThatStartedThisActivity = getIntent();
            if (intentThatStartedThisActivity != null) {
                if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                    mRecipe = getIntent().getParcelableExtra(Intent.EXTRA_TEXT);
                }
            }

            if (findViewById(R.id.stepListFragment) != null) {
                StepListFragment stepListFragment = StepListFragment.newInstance(mRecipe);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.stepListFragment, stepListFragment)
                        .commit();
            }

            if (findViewById(R.id.stepDetailFragment) != null) {
                mTwoPane = true;
                StepDetailFragment stepDetailFragment =
                        StepDetailFragment.newInstance(mRecipe, 0);;
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.stepDetailFragment, stepDetailFragment)
                        .commit();
            } else {
                mTwoPane = false;
            }

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
    public void onStepSelected(Step step) {
        if(mTwoPane) {
            StepDetailFragment stepDetailFragment =
                    StepDetailFragment.newInstance(mRecipe, step.getId());;
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.stepDetailFragment, stepDetailFragment)
                    .commit();
        } else {
            Class destinationClass = StepDetailActivity.class;
            Intent intentToStepsActivity = new Intent(this, destinationClass);
            intentToStepsActivity.putExtra(RECIPE, mRecipe);
            intentToStepsActivity.putExtra(STEP_ID, step.getId());
            startActivity(intentToStepsActivity);
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
