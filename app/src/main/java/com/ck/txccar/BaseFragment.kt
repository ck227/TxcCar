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
import android.webkit.WebView
import com.ck.util.Utils

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

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                Log.e("ck", "started")

                if (!Utils.hasNetWork(activity)) {
                    webView.visibility = View.GONE
                    errorView.visibility = View.VISIBLE
                } else {
                    loading.visibility = View.VISIBLE
                }
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                try {
                    loading.visibility = View.GONE
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


        //添加长按监听
//        webView.setOnLongClickListener(View.OnLongClickListener {
//
//        })



//        webView.setOnLongClickListener(View.OnLongClickListener {
//            return true
//        })
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
}