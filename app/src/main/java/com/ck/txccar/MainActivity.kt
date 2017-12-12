package com.ck.txccar

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.*
import com.ck.network.ApiStores
import kotlinx.android.synthetic.main.activity_main.*
import com.ck.util.BottomNavigationViewHelper
import com.ck.util.MyApplication
import android.view.ViewGroup
import android.widget.FrameLayout

class MainActivity : AppCompatActivity() {

    val baseUrl = ApiStores.API_SERVER_URL
    var baseFragment: BaseFragment? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
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

//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN)
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

}
