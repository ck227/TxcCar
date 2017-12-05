package com.ck.txccar

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.layout_login.*

/**
 * Created by cnbs5 on 2017/12/5.
 */

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_login)

        login.setOnClickListener {
            val intent = Intent()
            intent.setClass(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}