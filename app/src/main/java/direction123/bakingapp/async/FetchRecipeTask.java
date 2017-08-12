package direction123.bakingapp.async;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import direction123.bakingapp.adapters.RecipeAdapter;
import direction123.bakingapp.models.Recipe;
import direction123.bakingapp.utilities.NetworkUtils;
import direction123.bakingapp.utilities.RecipeUtils;

/**
 * Created by fangxiangwang on 8/9/17.
 */

public class FetchRecipeTask extends AsyncTask<Void, Void, List<Recipe>>{
    private RecyclerView mRecylerView;
    private RecipeAdapter mAdapter;
    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessageDisplay;

    public FetchRecipeTask(RecyclerView recyclerView, RecipeAdapter adapter,
                           ProgressBar loadingIndicator, TextView errorMessageDisplay) {
        this.mRecylerView = recyclerView;
        this.mAdapter = adapter;
        this.mLoadingIndicator = loadingIndicator;
        this.mErrorMessageDisplay = errorMessageDisplay;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    protected List<Recipe> doInBackground(Void... params) {
        URL recipeUrl = NetworkUtils.buildUrl();
        try {
            String recipeResponse = NetworkUtils.getResponseFromHttpUrl(recipeUrl);
            List<Recipe> recipeList = RecipeUtils.getRecipeList(recipeResponse);
            return recipeList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void showRecipeView () {
        mRecylerView.setVisibility(View.VISIBLE);
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
    }

    private void showErrorMessageView () {
        mRecylerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(List<Recipe> recipeList) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        if (recipeList != null) {
            showRecipeView ();
            mAdapter.setRecipeList(recipeList);
        } else {
            //show error if there is no internet connection
            showErrorMessageView();
        }
    }
}
