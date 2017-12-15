package com.ck.util;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by cnbs5 on 2017/12/7.
 */

public class AndroidAndJSInterface {

    @JavascriptInterface//一定要写，不然H5调不到这个方法
    public void phoneCall(String phone) {
//        Toast.makeText(MyApplication.getInstance().getApplicationContext(), "phone:" + phone, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_USER_ACTION);
        MyApplication.getInstance().getApplicationContext().startActivity(intent);
    }

    @JavascriptInterface
    public void savePic(String url) {

    }

}
