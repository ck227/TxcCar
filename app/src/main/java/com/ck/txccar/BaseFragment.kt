package com.ck.txccar

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
import android.widget.Toast
import com.ck.widget.LoadingDialog
import android.text.TextUtils
import android.webkit.WebView
import android.webkit.WebChromeClient
import com.ck.txccar.R.id.webView




/**
 * Created by cnbs5 on 2017/12/6.
 */

class BaseFragment : Fragment() {

//    var loadingDialog: LoadingDialog? = null

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
        webView.addJavascriptInterface(AndroidAndJSInterface(), "Android")
        webView.webViewClient = object : WebViewClient() {

            var hasError: Boolean? = false
            //            var loadingDialog = LoadingDialog(activity, R.style.MyCustomDialog)
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                loading.visibility = View.VISIBLE
                Log.e("ck", "started")
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                loading.visibility = View.GONE
                Log.e("ck", "finished")
//                if (hasError == true) {
//                    webView.visibility = View.GONE
//                    errorView.visibility = View.VISIBLE
//
//                    errorView.setOnClickListener {
//                        webView.loadUrl(url)
//                    }
//                } else {
//                    webView.visibility = View.VISIBLE
//                    errorView.visibility = View.GONE
//                    hasError = false
//                }
            }

            override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
                super.onReceivedError(view, errorCode, description, failingUrl)
                Log.e("ck", "error1")
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                Log.e("ck", "error2")
            }

            override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
                super.onReceivedHttpError(view, request, errorResponse)
                Log.e("ck", "httpError")
            }

        }

        /*webView.webChromeClient = object : WebChromeClient() {
            *//**
             * 当WebView加载之后，返回 HTML 页面的标题 Title
             * @param view
             * *
             * @param title
             *//*
            override fun onReceivedTitle(view: WebView, title: String) {
                //判断标题 title 中是否包含有“error”字段，如果包含“error”字段，则设置加载失败，显示加载失败的视图
                if (!TextUtils.isEmpty(title) && title.toLowerCase().contains("error")) {
                    loadError = true
                }
            }
        }*/
        webView.loadUrl(url)
    }

    private val PARAM = "PARAM"

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
}