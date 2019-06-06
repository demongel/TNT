package com.xchallenge.tnt;

import android.app.Application;

/**
 * created by  shakespace
 * 2019/5/20  0:38
 */
public class App extends Application {

    public static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static App getInstance() {
        return mInstance;
    }
}
