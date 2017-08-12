package direction123.bakingapp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import direction123.bakingapp.adapters.RecipeAdapter;
import direction123.bakingapp.async.FetchRecipeTask;
import direction123.bakingapp.models.Recipe;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler {
    private RecyclerView mRecyclerView;
    private RecipeAdapter mAdapter;
    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessageDisplay;
    public static final String RECIPE_LIST = "recipe_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_recipes);
        final int columns = getResources().getInteger(R.integer.grid_columns);
        GridLayoutManager layoutManager= new GridLayoutManager(this, columns);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new RecipeAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);
        mErrorMessageDisplay = (TextView) findViewById(R.id.error_message_display);

        if(savedInstanceState != null) {
            List<Recipe> recipeList = savedInstanceState.getParcelableArrayList(RECIPE_LIST);
            if(recipeList != null) {
                mAdapter.setRecipeList(recipeList);
            }
        } else {
            loadRecipeData();
        }
    }

    @Override
    public void onClick(Recipe recipe) {
        Class destinationClass = StepListActivity.class;
        Intent intentToStepsActivity = new Intent(this, destinationClass);
        intentToStepsActivity.putExtra(Intent.EXTRA_TEXT, recipe);
        startActivity(intentToStepsActivity);
    }

    private void loadRecipeData() {
        new FetchRecipeTask(mRecyclerView, mAdapter, mLoadingIndicator, mErrorMessageDisplay).execute();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        List<Recipe> recipeList = mAdapter.getRecipeList();
        outState.putParcelableArrayList(RECIPE_LIST, new ArrayList<>(recipeList));
    }
}
