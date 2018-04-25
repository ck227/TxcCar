package com.ck.txccar

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.widget.Toast
import com.ck.network.ApiCallback
import com.ck.network.ApiClient
import com.ck.network.ApiResult
import com.ck.util.MyApplication
import com.ck.util.Utils
import com.ck.widget.LoadingDialog
import kotlinx.android.synthetic.main.layout_login.*
import java.util.*

/**
 * Created by cnbs5 on 2017/12/5.
 */

class LoginActivity : BaseActivity() {

    var timer: Timer? = null
    var time = 60
    var dialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_login)

        goBack.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@LoginActivity, GuideActivity::class.java)
            startActivity(intent)
            finish()
        }

        getCode.setOnClickListener {
            if (checkValue()) {
                getCode()
            }
        }

        login.setOnClickListener {
            if (checkValue2()) {
                login()
            }
        }

        if (MyApplication.getInstance().userName.length > 0) {
            val intent = Intent()
            intent.setClass(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    fun getCode() {
        dialog = LoadingDialog(this, R.style.MyCustomDialog)
        dialog!!.show()
        val map = HashMap<String, String>()
        map.put("loginName", phone.text.toString())

        addSubscription(ApiClient.retrofit().sendCode(map), object : ApiCallback<ApiResult>() {

            override fun onSuccess(model: ApiResult) {
                if (model.code == 0) {
                    //倒计时
                    timerStart()
//                    timer()
                }
                Toast.makeText(applicationContext, model.msg, Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(msg: String?) {
                Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
            }

            override fun onFinish() {
                dialog!!.dismiss()
            }
        })
    }

    fun login() {
        dialog = LoadingDialog(this, R.style.MyCustomDialog)
        dialog!!.show()
        val map = HashMap<String, String>()
        map.put("loginName", phone.text.toString())
        map.put("verifyCode", code.text.toString())
        addSubscription(ApiClient.retrofit().login(map), object : ApiCallback<ApiResult>() {

            override fun onSuccess(model: ApiResult) {
                if (model.code == 0) {
                    MyApplication.getInstance().userName = phone.text.toString()
                    val intent = Intent()
                    intent.setClass(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(applicationContext, model.msg, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(msg: String?) {
                Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
            }

            override fun onFinish() {
                dialog!!.dismiss()
            }
        })
    }

    fun timerStart() {
        val timestamp: Long = 1000
        timer = Timer()
        val timerTask = object : TimerTask() {
            override fun run() {
                time--
                val message = handler.obtainMessage()
                message.what = 1


                handler.sendMessage(message)
            }
        }
        timer!!.schedule(timerTask, 100L, timestamp)
    }

    internal var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                1 -> if (time > 0) {
                    getCode.isEnabled = false
                    getCode.text = time.toString() + "秒后重试"
                    getCode.setBackgroundResource(R.drawable.shape_gray)
                } else {
                    timer!!.cancel()
                    getCode.isEnabled = true
                    getCode.text = "发送验证码"
                    time = 60
                    getCode.setBackgroundResource(R.drawable.selector_login_btn)
                }
            }
        }

    }

    fun checkValue(): Boolean {
        if (TextUtils.isEmpty(phone.text.toString())) {
            Toast.makeText(applicationContext, "请输入手机号", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!Utils.isMobileNO(phone.text.toString())) {
            Toast.makeText(applicationContext, "手机号错误", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    fun checkValue2(): Boolean {
        if (TextUtils.isEmpty(phone.text.toString())) {
            Toast.makeText(applicationContext, "请输入手机号", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!Utils.isMobileNO(phone.text.toString())) {
            Toast.makeText(applicationContext, "手机号错误", Toast.LENGTH_SHORT).show()
            return false
        }
        if (TextUtils.isEmpty(code.text.toString())) {
            Toast.makeText(applicationContext, "请输入验证码", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }


}