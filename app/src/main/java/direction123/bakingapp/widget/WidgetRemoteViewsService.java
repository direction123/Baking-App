package direction123.bakingapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by fangxiangwang on 8/18/17.
 */
//https://laaptu.wordpress.com/2013/07/19/android-app-widget-with-listview/

public class WidgetRemoteViewsService extends RemoteViewsService{
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetRemoteViewsFactory(this.getApplicationContext());
    }
}
