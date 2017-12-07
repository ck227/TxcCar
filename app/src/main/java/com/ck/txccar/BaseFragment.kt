package com.ck.txccar

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import kotlinx.android.synthetic.main.fragment_base.*
import android.webkit.WebView
import android.webkit.WebViewClient
import com.ck.txccar.R.id.webView
import com.ck.util.AndroidAndJSInterface
import com.ck.txccar.R.id.webView




/**
 * Created by cnbs5 on 2017/12/6.
 */

class BaseFragment : Fragment() {

//    var webView: WebView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_base, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = arguments.getString("url")

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
            //覆盖shouldOverrideUrlLoading 方法
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }
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