package com.ck.util;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by cnbs5 on 2017/12/7.
 */

public class MyApplication extends Application {

    private static MyApplication instance;
    private SharedPreferences sp;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        sp = getSharedPreferences("user", MODE_PRIVATE);
    }

    public static MyApplication getInstance() {
        return instance;
    }
}
