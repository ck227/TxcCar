package com.ck.util;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by cnbs5 on 2017/12/7.
 * <p>
 * 1.接口       好了
 * 2.网页进度   好了
 * 3.监听网页加载失败 ？？
 * 4.引导启动页  好了
 * 5.电话 好了
 * 6.渐变通知栏 好了
 * 7.监听网络不行 好了
 * 8.底部图标  好了
 * 9.长按下载图片
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

    public String getUserName() {
        return sp.getString("username", "");
    }

    public void setUserName(String username) {
        sp.edit().putString("username", username).commit();
    }

    public Boolean getIsFirst() {
        return sp.getBoolean("isFirst", true);
    }

    public void setIsFirst(Boolean isFirst) {
        sp.edit().putBoolean("isFirst", isFirst).commit();
    }


}
