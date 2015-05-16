package com.halfhour.tasks;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

/**
 * Created by 年高 on 2015/5/16.
 */
public class MainAppliaction extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }
}
