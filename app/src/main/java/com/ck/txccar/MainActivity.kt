package com.ck.txccar

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val baseUrl = "http://192.168.100.222:8080/txcCar/"
    var baseFragment: BaseFragment? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                supportFragmentManager.inTransaction {
                    baseFragment = BaseFragment.newInstance(baseUrl + "indexHtml/index.html")
                    replace(R.id.content, baseFragment, "TAG")
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_contact -> {
                supportFragmentManager.inTransaction {
                    baseFragment = BaseFragment.newInstance(baseUrl + "addressBookHtml/list.html")
                    replace(R.id.content, baseFragment, "TAG")
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_buy -> {
                supportFragmentManager.inTransaction {
                    baseFragment = BaseFragment.newInstance(baseUrl + "wantToBuyHtmlHtml/list.html")
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
                    baseFragment = BaseFragment.newInstance(baseUrl + "userHtml/user.html?loginName=15717174872 ")
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
