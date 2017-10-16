package com.bigstark.zangsisi;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by bigstark on 2017. 9. 20..
 */

public class ZangsisiApplication extends Application {

    private static ZangsisiApplication instance;

    public static ZangsisiApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Fresco.initialize(this);
    }
}
