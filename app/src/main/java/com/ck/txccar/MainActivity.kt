package com.ck.txccar

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.*
import kotlinx.android.synthetic.main.activity_main.*
import com.ck.util.BottomNavigationViewHelper
import com.ck.util.MyApplication
import android.view.ViewGroup
import android.widget.FrameLayout
import android.view.ContextMenu.ContextMenuInfo
import android.view.ContextMenu
import android.widget.Toast
import com.ck.network.*
import com.ck.util.AndroidAndJSInterface
import com.ck.widget.LoadingDialog
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import io.vrinda.kotlinpermissions.PermissionCallBack
import io.vrinda.kotlinpermissions.PermissionsActivity
import kotlinx.android.synthetic.main.fragment_base.*
import kotlinx.android.synthetic.main.layout_login.*
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import java.util.HashMap


class MainActivity : PermissionsActivity() {

    val baseUrl = ApiStores.API_SERVER_URL
    var baseFragment: BaseFragment? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
//                if(navigation.selectedItemId == R.id.navigation_home ){
//                    return@OnNavigationItemSelectedListener true
//                }
                supportFragmentManager.inTransaction {
                    baseFragment = BaseFragment.newInstance(baseUrl + "indexHtml/index.html?loginName=" + MyApplication.getInstance().userName)
                    replace(R.id.content, baseFragment, "TAG")
                }
//                navigation.menu.findItem(R.id.navigation_home).icon.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN)

                val states = arrayOf(intArrayOf(-android.R.attr.state_checked), intArrayOf(android.R.attr.state_checked))
                val colors = intArrayOf(resources.getColor(R.color.btn_blue), resources.getColor(R.color.btn_blue))
                val csl = ColorStateList(states, colors)
                navigation.itemTextColor = csl
                navigation.itemIconTintList = csl

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_contact -> {
                if (navigation.selectedItemId == R.id.navigation_contact) {
                    return@OnNavigationItemSelectedListener true
                }
                supportFragmentManager.inTransaction {
                    baseFragment = BaseFragment.newInstance(baseUrl + "addressBookHtml/list.html?loginName=" + MyApplication.getInstance().userName)
                    replace(R.id.content, baseFragment, "TAG")
                }
//                navigation.itemIconTintList = resources.getColorStateList(R.color.gray,null)
//                navigation.itemTextColor = resources.getColorStateList(R.color.gray,null)

                val states = arrayOf(intArrayOf(-android.R.attr.state_checked), intArrayOf(android.R.attr.state_checked))
                val colors = intArrayOf(resources.getColor(R.color.gray), resources.getColor(R.color.btn_blue))
                val csl = ColorStateList(states, colors)
                navigation.itemTextColor = csl
                navigation.itemIconTintList = csl

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_buy -> {
                if (navigation.selectedItemId == R.id.navigation_buy) {
                    return@OnNavigationItemSelectedListener true
                }
                supportFragmentManager.inTransaction {
                    baseFragment = BaseFragment.newInstance(baseUrl + "wantToBuyHtmlHtml/list.html?loginName=" + MyApplication.getInstance().userName)
                    replace(R.id.content, baseFragment, "TAG")
                }

                val states = arrayOf(intArrayOf(-android.R.attr.state_checked), intArrayOf(android.R.attr.state_checked))
                val colors = intArrayOf(resources.getColor(R.color.gray), resources.getColor(R.color.btn_blue))
                val csl = ColorStateList(states, colors)
                navigation.itemTextColor = csl
                navigation.itemIconTintList = csl


                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_send -> {
                if (navigation.selectedItemId == R.id.navigation_send) {
                    return@OnNavigationItemSelectedListener true
                }
                supportFragmentManager.inTransaction {
                    baseFragment = BaseFragment.newInstance(baseUrl + "categoryDetailsHtml/add.html?loginName=" + MyApplication.getInstance().userName)
                    replace(R.id.content, baseFragment, "TAG")
                }

                val states = arrayOf(intArrayOf(-android.R.attr.state_checked), intArrayOf(android.R.attr.state_checked))
                val colors = intArrayOf(resources.getColor(R.color.gray), resources.getColor(R.color.btn_blue))
                val csl = ColorStateList(states, colors)
                navigation.itemTextColor = csl
                navigation.itemIconTintList = csl


                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_my -> {
                if (navigation.selectedItemId == R.id.navigation_my) {
                    return@OnNavigationItemSelectedListener true
                }
                supportFragmentManager.inTransaction {
                    baseFragment = BaseFragment.newInstance(baseUrl + "userHtml/user.html?loginName=" + MyApplication.getInstance().userName)
                    replace(R.id.content, baseFragment, "TAG")
                }

                val states = arrayOf(intArrayOf(-android.R.attr.state_checked), intArrayOf(android.R.attr.state_checked))
                val colors = intArrayOf(resources.getColor(R.color.gray), resources.getColor(R.color.btn_blue))
                val csl = ColorStateList(states, colors)
                navigation.itemTextColor = csl
                navigation.itemIconTintList = csl
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navigation)
        bottomNavigationView.selectedItemId = R.id.navigation_home

        //去掉底部动画
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView)

        //渐变的通知栏颜色
        val decorViewGroup = window.decorView as ViewGroup
        val statusBarView = View(window.getContext()) as View
        val statusBarHeight = getStatusBarHeight()
        val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, statusBarHeight)
        params.gravity = Gravity.TOP
        statusBarView.setLayoutParams(params)
        statusBarView.setBackgroundResource(R.drawable.shape_status_bar)
        decorViewGroup.addView(statusBarView)

        shareClass = AndroidAndJSInterface(this)

        checkVersion()
    }

    fun getStatusBarHeight(): Int {
        var statusBarHeight = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight
    }


    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && baseFragment!!.getWebView()!!.canGoBack()) {
            baseFragment!!.getWebView()!!.goBack()// 返回前一个页面
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    var shareClass: AndroidAndJSInterface? = null

    fun goShareWeixin(path : String) {
        Toast.makeText(this, "shareWeixin", Toast.LENGTH_LONG).show()
        requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_LOGS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.SET_DEBUG_APP,
                Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS,
                Manifest.permission.WRITE_APN_SETTINGS), object : PermissionCallBack {
            override fun permissionGranted() {
                super.permissionGranted()
                var shareAction: ShareAction? = ShareAction(this@MainActivity)
                shareAction!!.setPlatform(SHARE_MEDIA.WEIXIN)
                        .withText("hello")
                        .setCallback(object : UMShareListener {

                            override fun onStart(platform: SHARE_MEDIA) {

                            }

                            override fun onResult(platform: SHARE_MEDIA) {
//            Toast.makeText(this, "分享成功", Toast.LENGTH_SHORT).show()
                            }

                            override fun onError(platform: SHARE_MEDIA, t: Throwable) {
                                //Toast.makeText(this@ShareDetailActivity, "失败" + t.message, Toast.LENGTH_LONG).show()
                            }

                            override fun onCancel(platform: SHARE_MEDIA) {
//            Toast.makeText(this@MainActivity, "取消了", Toast.LENGTH_LONG).show()
                            }
                        })
                        .share()
            }

            override fun permissionDenied() {
                super.permissionDenied()
                Log.v("Call permissions", "Denied")
            }
        })
    }

    fun goShareQQ(path : String) {
        Toast.makeText(this, "shareQQ", Toast.LENGTH_LONG).show()
        requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_LOGS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.SET_DEBUG_APP,
                Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS,
                Manifest.permission.WRITE_APN_SETTINGS), object : PermissionCallBack {
            override fun permissionGranted() {
                super.permissionGranted()
                var shareAction: ShareAction? = ShareAction(this@MainActivity)
                shareAction!!.setPlatform(SHARE_MEDIA.QQ)
                        .withText("hello")
                        .setCallback(umShareListener)
                        .share()
            }

            override fun permissionDenied() {
                super.permissionDenied()
                Log.v("Call permissions", "Denied")
            }
        })
    }

    object umShareListener : UMShareListener {
        override fun onStart(platform: SHARE_MEDIA) {

        }

        override fun onResult(platform: SHARE_MEDIA) {
//            Toast.makeText(this, "分享成功", Toast.LENGTH_SHORT).show()
        }

        override fun onError(platform: SHARE_MEDIA, t: Throwable) {
            //Toast.makeText(this@ShareDetailActivity, "失败" + t.message, Toast.LENGTH_LONG).show()
        }

        override fun onCancel(platform: SHARE_MEDIA) {
//            Toast.makeText(this@MainActivity, "取消了", Toast.LENGTH_LONG).show()
        }
    }


    fun checkVersion() {
        var version = "1.0"
        try {
            val manager = this.packageManager
            val info = manager.getPackageInfo(this.packageName, 0)
            version = info.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        val map = HashMap<String, String>()
        map.put("versionNumber", version)

        addSubscription(ApiClient.retrofit().checkVersion(map), object : ApiCallback<VersionResult>() {

            override fun onSuccess(model: VersionResult) {
                if (model.code == 0) {
                    val version = model.version
                    val b = AlertDialog.Builder(this@MainActivity)
                    b.setTitle("发现新的版本" + version.versioNumber)
                    b.setMessage(version.versionContent)
                    b.setPositiveButton("更新") { dialog, which ->
                        //这里打开链接
                        val uri = Uri.parse(version.versionUrl)
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(intent)
                        if (version.isVersion == 1) {
                            finish()
                        }
                    }
                    if (version.isVersion == 1) {
                        b.setCancelable(false)
                    } else {
                        b.setNegativeButton("取消") { dialog, which -> }
                    }
                    b.create().show()
                }
//                Toast.makeText(applicationContext, model.msg, Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(msg: String?) {
                Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
            }

            override fun onFinish() {

            }
        })
    }

    val mCompositeSubscription: CompositeSubscription = CompositeSubscription()

    fun <M> addSubscription(observable: Observable<M>, subscriber: Subscriber<M>) {
        mCompositeSubscription.add(
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(subscriber))
    }

}
