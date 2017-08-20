package direction123.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import direction123.bakingapp.adapters.RecipeAdapter;
import direction123.bakingapp.models.Recipe;
import direction123.bakingapp.models.SOService;
import direction123.bakingapp.utilities.ApiUtils;
import direction123.bakingapp.utilities.DBUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler {
    @BindView(R.id.recyclerview_recipes) RecyclerView mRecyclerView;
    @BindView(R.id.loading_indicator) ProgressBar mLoadingIndicator;
    @BindView(R.id.error_message_display) TextView mErrorMessageDisplay;

    private RecipeAdapter mAdapter;
    private SOService mService;
    public static final String RECIPE_LIST = "recipe_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mService = ApiUtils.getSOService();

        final int columns = getResources().getInteger(R.integer.grid_columns);
        GridLayoutManager layoutManager= new GridLayoutManager(this, columns);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new RecipeAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

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
        mService.getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

                if(response.isSuccessful()) {
                    mAdapter.setRecipeList(response.body());
                    DBUtils.saveWidgetToDB(response.body());
                    updateWidget();
                }else {
                    int statusCode  = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d("MainActivity", "error loading from API");
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        List<Recipe> recipeList = mAdapter.getRecipeList();
        if (recipeList != null) {
            outState.putParcelableArrayList(RECIPE_LIST, new ArrayList<>(recipeList));
        }
    }

    private void updateWidget() {
        Intent intent = new Intent(this, RecipeWidgetProvider.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), RecipeWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(intent);
    }
}
