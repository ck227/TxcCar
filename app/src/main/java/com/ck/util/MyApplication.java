package com.ck.util;

import android.app.Application;
import android.content.SharedPreferences;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

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
 * 9.长按下载图片 好了
 * 10. android  4.4 不管了
 * 11.版本更新  好了，需要测试
 * 12.百度上线 上线中...
 * 13. 分享
 */

public class MyApplication extends Application {

//    APP ID       1106642778   APP KEY    WysPXETPlSkbz9le
    {

        //备份下签名 70V06nPlUGaKBtLOAkUAlmi8KtHrM8wj  微信开放平台的

        PlatformConfig.setWeixin("wxbb054e3ddd8f21be", "cc4a476bc84cad050b2f5232dc3d399f");
        PlatformConfig.setQQZone("1106642778", "WysPXETPlSkbz9le");
        //PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
    }

    private static MyApplication instance;
    private SharedPreferences sp;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        sp = getSharedPreferences("user", MODE_PRIVATE);
        Config.DEBUG = true;
        UMShareAPI.get(this);
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
