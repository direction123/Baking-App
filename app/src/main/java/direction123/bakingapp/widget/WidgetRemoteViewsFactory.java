package direction123.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import direction123.bakingapp.R;
import direction123.bakingapp.data.RecipeWidget;
import direction123.bakingapp.models.Recipe;

/**
 * Created by fangxiangwang on 8/18/17.
 */
//https://laaptu.wordpress.com/2013/07/19/android-app-widget-with-listview/
public class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{
    private ArrayList<Integer> mFavoriteRecipeList = new ArrayList<>();
    private List<RecipeWidget> mRecipeWidgetList = new ArrayList<>();
    private Context mContext = null;
    private int mWidgetId;

    public WidgetRemoteViewsFactory(Context context) {
        this.mContext = context;
        this.mRecipeWidgetList = new Select()
                .from(RecipeWidget.class)
                .orderBy("recipe_id ASC")
                .execute();
    }

    private void loadRecipeItem() {
        for (int i = 0; i < 10; i++) {
            mFavoriteRecipeList.add(i);
        }
        mRecipeWidgetList = new Select()
                    .from(RecipeWidget.class)
                    .orderBy("recipe_id ASC")
                    .execute();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public int getCount() {
        return mRecipeWidgetList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Log.v("xxddddx", String.valueOf(mRecipeWidgetList.size()));

        RemoteViews remoteViews = new RemoteViews(
                mContext.getPackageName(), R.layout.widget_list_item
        );
        RecipeWidget recipeWidget = mRecipeWidgetList.get(position);
        if (recipeWidget != null) {
            remoteViews.setTextViewText(R.id.widget_recipe_name, recipeWidget.name);
            String servings = "Servings: " + recipeWidget.servings;
            remoteViews.setTextViewText(R.id.widget_recipe_servings, servings);
            remoteViews.setTextViewText(R.id.widget_recipe_ingredients, recipeWidget.ingredients);
        }

        return remoteViews;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onDataSetChanged() {
        Log.v("xxx", "ondatasetchanged");
        Log.v("xxx", String.valueOf(mRecipeWidgetList.size()));
        loadRecipeItem();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }
}

