package com.ck.txccar

import android.Manifest
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_base.*
import com.ck.util.AndroidAndJSInterface
import android.graphics.Bitmap
import android.webkit.*
import android.webkit.WebView
import com.ck.util.Utils
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.widget.Toast
import android.graphics.BitmapFactory
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL
import android.content.Intent
import android.net.Uri
import android.os.Environment.getExternalStorageDirectory
import android.os.Handler
import android.os.Message
import io.vrinda.kotlinpermissions.PermissionCallBack
import io.vrinda.kotlinpermissions.PermissionFragment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


/**
 * Created by cnbs5 on 2017/12/6.
 */

class BaseFragment : PermissionFragment() {

    var picUrl: String? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_base, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = arguments.getString("url")

        webView.isHorizontalScrollBarEnabled = false
        webView.settings.javaScriptEnabled = true
        webView.settings.builtInZoomControls = true
        webView.settings.useWideViewPort = true
        webView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        webView.settings.loadWithOverviewMode = false
        webView.settings.allowContentAccess = false
        webView.settings.domStorageEnabled = true
        //设置支持js调用java
        webView.addJavascriptInterface(AndroidAndJSInterface(activity as MainActivity), "Android")
        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                Log.e("ck", "started")

                try {
                    if (!Utils.hasNetWork(activity)) {
                        webView.visibility = View.GONE
                        errorView.visibility = View.VISIBLE

                        errorView.setOnClickListener {
                            webView.loadUrl(url)
                        }
                        Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show()
                    } else {
                        loading.visibility = View.VISIBLE
                    }
                } catch(e: Exception) {
                }
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                try {
                    loading.visibility = View.GONE
                } catch(e: Exception) {

                }
                try {
                    if (!Utils.hasNetWork(activity)) {
                        webView.visibility = View.GONE
                        errorView.visibility = View.VISIBLE
                    } else {
                        webView.visibility = View.VISIBLE
                        errorView.visibility = View.GONE
                    }
                } catch(e: Exception) {
                }
                Log.e("ck", "finished")
            }

            override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
                super.onReceivedError(view, errorCode, description, failingUrl)
                Log.e("ck", "onReceivedError1")
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                Log.e("ck", "onReceivedError2")
            }

            override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
                super.onReceivedHttpError(view, request, errorResponse)
                Log.e("ck", "onReceivedHttpError")
            }
        }
        webView.loadUrl(url)


        webView.setOnLongClickListener(View.OnLongClickListener {

            val hitTestResult = webView.hitTestResult
            // 如果是图片类型或者是带有图片链接的类型
            if (hitTestResult.type == WebView.HitTestResult.IMAGE_TYPE || hitTestResult.type == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {

                requestPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, object : PermissionCallBack {
                    override fun permissionGranted() {
                        super.permissionGranted()
                        Log.v("Call permissions", "Granted")
                        val hitTestResult = webView.hitTestResult
                        // 如果是图片类型或者是带有图片链接的类型
                        if (hitTestResult.type == WebView.HitTestResult.IMAGE_TYPE || hitTestResult.type == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                            // 弹出保存图片的对话框
                            val builder = AlertDialog.Builder(context)
                            builder.setTitle("提示")
                            builder.setMessage("保存图片到本地？")
                            builder.setPositiveButton("保存", DialogInterface.OnClickListener { dialogInterface, i ->
                                val picUrl2 = hitTestResult.extra//获取图片链接
                                picUrl = picUrl2
                                Log.e("ck", picUrl)
                                //保存图片到相册
                                Thread(Runnable {
                                    url2bitmap(picUrl2)
                                }).start()
                            })
                            builder.setNegativeButton("取消", // 自动dismiss
                                    DialogInterface.OnClickListener { dialogInterface, i -> })
                            val dialog = builder.create()
                            dialog.show()
                        }
                    }

                    override fun permissionDenied() {
                        super.permissionDenied()
                        Log.v("Call permissions", "Denied")
                    }
                })

                return@OnLongClickListener true
            }

            false//保持长按可以复制文字
        })
    }

    companion object {
        fun newInstance(url: String): BaseFragment {
            var args: Bundle = Bundle()
            args.putString("url", url)
            var editFragment: BaseFragment = newInstance()
            editFragment.arguments = args
            return editFragment
        }

        fun newInstance(): BaseFragment {
            return BaseFragment()
        }
    }

    fun getWebView(): WebView {
        if (webView == null) {
            Log.e("k", "sdfsfsfsf")
        }
        return webView
    }


    fun url2bitmap(url: String) {
        var bm: Bitmap? = null
        try {
            val iconUrl = URL(url)
            val conn = iconUrl.openConnection()
            val http = conn as HttpURLConnection
            val length = http.getContentLength()
            conn.connect()
            // 获得图像的字符流
            val iss = conn.getInputStream()
            val bis = BufferedInputStream(iss, length)
            bm = BitmapFactory.decodeStream(bis)
            bis.close()
            iss.close()
            if (bm != null) {
                save2Album(bm)
            }
        } catch (e: Exception) {
//            runOnUiThread(Runnable { Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT).show() })
            Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    fun save2Album(bitmap: Bitmap) {
        val appDir = File(getExternalStorageDirectory(), "淘现车")
        if (!appDir.exists()) appDir.mkdir()
        val str = picUrl!!.split("/")
        val fileName = str[str.size - 1]
        val file = File(appDir, fileName)
        try {
            val fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
            onSaveSuccess(file)
        } catch (e: IOException) {
//            runOnUiThread(Runnable { Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT).show() })
            Toast.makeText(context, "保存失败!", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }

    }

    fun onSaveSuccess(file: File) {
        activity.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)))
        handler.sendEmptyMessage(1)
    }

//    internal var handler: Handler = object : Handler() {
//        override fun handleMessage(msg: Message) {
//            when (msg.what) {
//                Toast.makeText(activity,"sfdf",Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

    internal var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                1 -> Toast.makeText(context, "保存成功！", Toast.LENGTH_SHORT).show()

            }
        }

    }

}