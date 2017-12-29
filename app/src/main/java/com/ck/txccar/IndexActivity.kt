package com.ck.txccar

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ck.util.MyApplication

/**
 * Created by cnbs5 on 2017/12/8.
 */
class IndexActivity : Runnable, AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_index)
    }

    override fun onStart() {
        super.onStart()
        Thread(this).start()
    }

    override fun run() {
        try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        val intent: Intent
        if (MyApplication.getInstance().isFirst) {
            MyApplication.getInstance().isFirst = false
//            intent = Intent(this@IndexActivity, GuideActivity::class.java)
            intent = Intent(this@IndexActivity, LoginActivity::class.java)
            startActivity(intent)
        } else {
            val username = MyApplication.getInstance().userName
            if (username != null && username.length > 0) {
                intent = Intent(this@IndexActivity, MainActivity::class.java)
                startActivity(intent)
            } else {
                intent = Intent(this@IndexActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        finish()

    }
}