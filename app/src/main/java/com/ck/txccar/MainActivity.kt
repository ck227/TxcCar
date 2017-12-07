package com.ck.txccar

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import com.ck.network.ApiStores
import kotlinx.android.synthetic.main.activity_main.*
import com.ck.util.BottomNavigationViewHelper

class MainActivity : AppCompatActivity() {

    val baseUrl = ApiStores.API_SERVER_URL
    var baseFragment: BaseFragment? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                supportFragmentManager.inTransaction {
                    baseFragment = BaseFragment.newInstance(baseUrl + "indexHtml/index.html?loginName=15717174872")
                    replace(R.id.content, baseFragment, "TAG")
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_contact -> {
                supportFragmentManager.inTransaction {
                    baseFragment = BaseFragment.newInstance(baseUrl + "addressBookHtml/list.html?loginName=15717174872")
                    replace(R.id.content, baseFragment, "TAG")
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_buy -> {
                supportFragmentManager.inTransaction {
                    baseFragment = BaseFragment.newInstance(baseUrl + "wantToBuyHtmlHtml/list.html?loginName=15717174872")
                    replace(R.id.content, baseFragment, "TAG")
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_send -> {
                supportFragmentManager.inTransaction {
                    baseFragment = BaseFragment.newInstance(baseUrl + "categoryDetailsHtml/add.html?loginName=15717174872")
                    replace(R.id.content, baseFragment, "TAG")
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_my -> {
                supportFragmentManager.inTransaction {
                    baseFragment = BaseFragment.newInstance(baseUrl + "userHtml/user.html?loginName=15717174872")
                    replace(R.id.content, baseFragment, "TAG")
                }
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

        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView)
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
