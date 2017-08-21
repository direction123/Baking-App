package direction123.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import direction123.bakingapp.models.Recipe;
import direction123.bakingapp.models.Step;
import direction123.bakingapp.utilities.DBUtils;

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

    public void toggleWidget (View v) {
        Button button = (Button) v;
        String text = button.getText().toString();
        if (text.equals(getString(R.string.add_to_widget))) {
            button.setText(getString(R.string.remove_from_widget));
            DBUtils.saveWidgetToDB(mRecipe);
        } else if (text.equals(getString(R.string.remove_from_widget))) {
            button.setText(getString(R.string.add_to_widget));
            DBUtils.deleteWidgetFromDB(mRecipe);
        }
        updateWidget();
    }

    private void updateWidget() {
        Intent intent = new Intent(this, RecipeWidgetProvider.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), RecipeWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(intent);
    }
}
