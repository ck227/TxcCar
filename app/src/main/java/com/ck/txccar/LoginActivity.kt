package com.ck.txccar

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.ck.network.ApiCallback
import com.ck.network.ApiClient
import com.ck.network.ApiResult
import com.ck.util.Utils
import com.ck.widget.LoadingDialog
import kotlinx.android.synthetic.main.layout_login.*
import java.util.HashMap

/**
 * Created by cnbs5 on 2017/12/5.
 */

class LoginActivity : BaseActivity() {

    var dialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_login)

        getCode.setOnClickListener {
            if (checkValue()) {
                getCode()
            }
        }

        login.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getCode() {
        dialog = LoadingDialog(this, R.style.MyCustomDialog)
        dialog!!.show()
        val map = HashMap<String, Any>()
        map.put("loginName", phone.text.toString())

        addSubscription(ApiClient.retrofit().sendCode(map), object : ApiCallback<ApiResult>() {
            override fun onSuccess(model: ApiResult) {
                Log.d("ck", "code=" + model.baseResponse.code)//输出“city=无锡,cityid=101190201”
            }

            override fun onFailure(msg: String?) {
                Log.d("ck", "onFailure=" + msg)
            }

            override fun onFinish() {
                Log.d("ck", "onFinish")
            }
        })
    }


//    192.168.100.222:8080/txcCar/userFront/login.html?loginName=15717174872&verifyCode=0000
    private fun checkValue(): Boolean {
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


}