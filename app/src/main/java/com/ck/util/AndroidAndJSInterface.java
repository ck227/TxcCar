package com.ck.util;

import android.content.Intent;
import android.net.Uri;
import android.webkit.JavascriptInterface;
import android.widget.Toast;
import com.ck.txccar.MainActivity;

/**
 * Created by cnbs5 on 2017/12/7.
 */

public class AndroidAndJSInterface {

    public MainActivity aActivity = null;

    @JavascriptInterface//一定要写，不然H5调不到这个方法
    public void phoneCall(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_USER_ACTION);
        MyApplication.getInstance().getApplicationContext().startActivity(intent);
    }

    @JavascriptInterface
    public void shareweixin(String path) {
        aActivity.goShareWeixin(path);
    }

    @JavascriptInterface
    public void shareqq(String path,String imagePatch,String content,String title) {
        aActivity.goShareQQ(path,imagePatch,content,title);
    }



    @JavascriptInterface
    public void shareqqapp(String path) {
        aActivity.goShareQQApp(path);
    }

    public AndroidAndJSInterface(MainActivity activity){
        aActivity = activity;
    }


}
