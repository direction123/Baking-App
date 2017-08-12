package direction123.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import direction123.bakingapp.adapters.RecipeAdapter;
import direction123.bakingapp.async.FetchRecipeTask;
import direction123.bakingapp.models.Recipe;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler {
    private RecyclerView mRecyclerView;
    private RecipeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_recipes);
        //set layoutmanager
        final int columns = getResources().getInteger(R.integer.grid_columns);
        GridLayoutManager layoutManager= new GridLayoutManager(this, columns);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        //set adapter
        mAdapter = new RecipeAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        //load recipe
        loadRecipeData();
    }

    @Override
    public void onClick(Recipe recipe) {
        Class destinationClass = StepListActivity.class;
        Intent intentToStepsActivity = new Intent(this, destinationClass);
        intentToStepsActivity.putExtra(Intent.EXTRA_TEXT, recipe);
        startActivity(intentToStepsActivity);
    }

    private void loadRecipeData() {
        new FetchRecipeTask(mRecyclerView, mAdapter).execute();
    }
}
