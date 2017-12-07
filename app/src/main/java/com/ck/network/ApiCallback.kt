package com.ck.network

import android.util.Log
import retrofit2.adapter.rxjava.HttpException
import rx.Subscriber

/**
 * Created by cnbs5 on 2017/12/7.
 */
abstract class ApiCallback<M> : Subscriber<M>() {

    abstract fun onSuccess(model: M)
    abstract fun onFailure(msg: String?)
    abstract fun onFinish()

    override fun onCompleted() {
        onFinish()
    }

    override fun onNext(m: M) {
        onSuccess(m)
    }

    override fun onError(e: Throwable?) {
        //这块应该可以优化
        if (e is HttpException) {
            val httpException = e
            //httpException.response().errorBody().string()
            val code = httpException.code()
            var msg = httpException.message
            Log.d("ck", "code=" + code)
            if (code == 504) {
                msg = "网络不给力"
            }
            if (code == 502 || code == 404) {
                msg = "服务器异常，请稍后再试"
            }
            onFailure(msg)
        } else {
            onFailure(e.toString())
        }
        onFinish()
    }
}