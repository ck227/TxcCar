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
    public void shareweixin(String path,String imagePatch,String content,String title) {
        aActivity.goShareWeixin(path,imagePatch,content,title);
    }

    @JavascriptInterface
    public void shareweixinkj(String path,String imagePatch,String content,String title) {
        aActivity.goShareWeixinkj(path,imagePatch,content,title);
    }

    @JavascriptInterface
    public void shareqq(String path,String imagePatch,String content,String title) {
        aActivity.goShareQQ(path,imagePatch,content,title);
    }

    @JavascriptInterface
    public void shareqqkj(String path,String imagePatch,String content,String title) {
        aActivity.goShareQQkj(path,imagePatch,content,title);
    }



    @JavascriptInterface
    public void shareqqapp(String path) {
        aActivity.goShareQQApp(path);
    }

    @JavascriptInterface
    public void shareqqakj(String path) {
        aActivity.goShareQQakj(path);
    }

    @JavascriptInterface
    public void shareweixinapp(String path) {
        aActivity.goShareWeixinApp(path);
    }

    @JavascriptInterface
    public void shareweixinkj(String path) {
        aActivity.goShareWeixinAppkj(path);
    }

    public AndroidAndJSInterface(MainActivity activity){
        aActivity = activity;
    }


}
