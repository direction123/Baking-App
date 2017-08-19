package direction123.bakingapp.applications;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by fangxiangwang on 8/19/17.
 */

public class StethoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
