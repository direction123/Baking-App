package direction123.bakingapp.utilities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import direction123.bakingapp.models.Ingredient;
import direction123.bakingapp.models.Recipe;
import direction123.bakingapp.models.Step;

/**
 * Created by fangxiangwang on 8/9/17.
 */

public class RecipeUtils
{
    public static List<Recipe> getRecipeList(String recipesJSONString) {
        List<Recipe> recipeList = new ArrayList<>();
        try {
            JSONArray recipeArray=new JSONArray(recipesJSONString);
            for (int i = 0; i < recipeArray.length(); i++) {
                JSONObject r = recipeArray.getJSONObject(i);
                Recipe recipe = buildRecipe(r);
                recipeList.add(recipe);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipeList;
    }

    private static Recipe buildRecipe(JSONObject r) {
        try {
            List<Ingredient> ingredientList = new ArrayList<>();
            JSONArray ingredientJSONArray = r.getJSONArray("ingredients");
            for (int i = 0; i < ingredientJSONArray.length(); i++) {
                JSONObject ingredientJSON = ingredientJSONArray.getJSONObject(i);
                Ingredient ingredient = new Ingredient(
                        ingredientJSON.getDouble("quantity"),
                        ingredientJSON.getString("measure"),
                        ingredientJSON.getString("ingredient")
                );
               ingredientList.add(ingredient);
            }

            List<Step> stepList = new ArrayList<>();
            JSONArray stepJSONArray = r.getJSONArray("steps");
            for (int i = 0; i < stepJSONArray.length(); i++) {
                JSONObject stepJSON = stepJSONArray.getJSONObject(i);
                Step step = new Step(
                        i,
                        stepJSON.getString("shortDescription"),
                        stepJSON.getString("description"),
                        stepJSON.getString("videoURL")
                );
                stepList.add(step);
            }

            Recipe recipe = new Recipe(
                    r.getInt("id"),
                    r.getString("name"),
                    ingredientList,
                    stepList,
                    r.getInt("servings")
            );
            return recipe;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
