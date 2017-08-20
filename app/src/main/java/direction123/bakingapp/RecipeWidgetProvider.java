package direction123.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import direction123.bakingapp.widget.WidgetRemoteViewsService;

/**
 * Implementation of App Widget functionality.
 */

public class RecipeWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_list);

            Intent intent = new Intent(context, WidgetRemoteViewsService.class);
            remoteViews.setRemoteAdapter(R.id.widget_list_ivew, intent);

            Intent homeIntent = new Intent(context, MainActivity.class);
            PendingIntent homePendingIntent = PendingIntent.getActivity(context, 0, homeIntent, 0);
            remoteViews.setOnClickPendingIntent(R.id.widget_title, homePendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("dddd", "broadcast receiver");
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
        ComponentName thisWidget = new ComponentName(context.getApplicationContext(), RecipeWidgetProvider.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        if (appWidgetIds != null && appWidgetIds.length > 0) {
            onUpdate(context, appWidgetManager, appWidgetIds);
            for (int appWidgetId : appWidgetIds) {
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_list_ivew);
            }
        }
    }
}

