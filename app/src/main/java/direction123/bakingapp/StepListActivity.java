package direction123.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import direction123.bakingapp.models.Recipe;

public class StepListActivity extends AppCompatActivity {
    private Recipe mRecipe;
    private boolean mTwoPane;
    public static  final String RECIPE = "recipe";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

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
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.stepDetailFragment, stepDetailFragment)
                    .commit();
        } else {
            mTwoPane = false;
        }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(mRecipe != null) {
            getSupportActionBar().setTitle(mRecipe.getNmae());
        }
    }
}
