package direction123.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import direction123.bakingapp.R;

/**
 * Created by fangxiangwang on 8/18/17.
 */
//https://laaptu.wordpress.com/2013/07/19/android-app-widget-with-listview/
public class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{
    private ArrayList<Integer> mFavoriteRecipeList = new ArrayList<>();
    private Context mContext = null;
    private int mWidgetId;

    public WidgetRemoteViewsFactory(Context context) {
        this.mContext = context;
    }

    private void loadFavoriateRecipeItem() {
        for (int i = 0; i < 10; i++) {
            mFavoriteRecipeList.add(i);
        }
    }

    @Override
    public void onCreate() {

    }

    @Override
    public int getCount() {
        return mFavoriteRecipeList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(
                mContext.getPackageName(), R.layout.widget_list_item
        );
        remoteViews.setTextViewText(R.id.widget_recipe_name, String.valueOf(mFavoriteRecipeList.get(position)));
        remoteViews.setTextViewText(R.id.widget_recipe_servings, String.valueOf(mFavoriteRecipeList.get(position)));
        remoteViews.setTextViewText(R.id.widget_recipe_ingredients, String.valueOf(mFavoriteRecipeList.get(position)));

        return remoteViews;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onDataSetChanged() {
        loadFavoriateRecipeItem();
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

