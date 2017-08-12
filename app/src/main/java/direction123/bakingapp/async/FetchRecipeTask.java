package direction123.bakingapp.async;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

    public FetchRecipeTask(RecyclerView recyclerView, RecipeAdapter adapter) {
        this.mRecylerView = recyclerView;
        this.mAdapter = adapter;
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

    @Override
    protected void onPostExecute(List<Recipe> recipeList) {
        mAdapter.setRecipeList(recipeList);
    }
}
