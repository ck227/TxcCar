package com.ck.txccar

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

/**
 * Created by cnbs5 on 2017/12/7.
 */
open class BaseActivity : AppCompatActivity() {

    val mCompositeSubscription: CompositeSubscription = CompositeSubscription()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        if (mCompositeSubscription.hasSubscriptions()) {
            //取消注册，以避免内存泄露
            mCompositeSubscription.unsubscribe()
        }
        super.onDestroy()
    }

    open fun <M> addSubscription(observable: Observable<M>, subscriber: Subscriber<M>) {
        mCompositeSubscription.add(
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(subscriber))
    }
}