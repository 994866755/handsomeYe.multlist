package com.example.kylinarm.multilisttest;

import android.app.Application;

/**
 * Created by Administrator on 2017/9/8.
 */

public class AppApplication extends Application {

    private static AppApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static AppApplication getInstance() {
        return instance;
    }

}
