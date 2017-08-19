package direction123.bakingapp.applications;

import com.activeandroid.ActiveAndroid;

/**
 * Created by fangxiangwang on 8/19/17.
 */

public class ActiveAndroidApplication  extends  StethoApplication{
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
