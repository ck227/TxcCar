package com.ck.util;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * Created by cnbs5 on 2017/12/7.
 */

public class AndroidAndJSInterface {

    @JavascriptInterface//一定要写，不然H5调不到这个方法
    public void phoneCall(String phone) {
        Log.e("ck", phone + "ck");
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        MyApplication.getInstance().getApplicationContext().startActivity(intent);
    }

}
