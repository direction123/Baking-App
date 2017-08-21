package direction123.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import direction123.bakingapp.models.Ingredient;
import direction123.bakingapp.models.Recipe;
import direction123.bakingapp.models.Step;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;

/**
 * Created by fangxiangwang on 8/20/17.
 */

//http://blog.sqisland.com/2015/04/espresso-21-activitytestrule.html
@RunWith(AndroidJUnit4.class)
public class StepListActivityTest {
    @Rule
    public ActivityTestRule<StepListActivity> mActivityTestRule = new ActivityTestRule<>(
            StepListActivity.class,
            true,  // initialTouchMode
            false);  // launchActivity. False to set intent per method

    @Test
    public void recipeDetailTest() {
        Ingredient ingredient1 = new Ingredient(1.5, "TSP", "salt");
        Ingredient ingredient2 = new Ingredient(5.0, "TBLSP", "vanilla");
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(ingredient1);
        ingredientList.add(ingredient2);

        Step step1 = new Step(
                0,
                "Recipe Introduction",
                "Recipe Introduction",
                "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4",
                ""
        );
        Step step2 = new Step(
                1,
                "Starting prep",
                "Starting prep",
                "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4",
                ""
        );
        List<Step> stepList = new ArrayList<>();
        stepList.add(step1);
        stepList.add(step2);

        Recipe recipe = new Recipe();
        recipe.setName("Test Recipe Name");
        recipe.setServings(5);
        recipe.setIngredients(ingredientList);
        recipe.setSteps(stepList);

        Context mockContext = InstrumentationRegistry
                .getInstrumentation().getTargetContext();
        Intent recipeDetailIntent = new Intent(mockContext, StepListActivity.class);
        recipeDetailIntent.putExtra(Intent.EXTRA_TEXT, recipe);


        mActivityTestRule.launchActivity(recipeDetailIntent);
        onView(withId(R.id.ingredients_list)).check(matches(isCompletelyDisplayed()));
    }
}
