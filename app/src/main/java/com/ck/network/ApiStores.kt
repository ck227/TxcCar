package com.ck.network

import retrofit2.http.GET
import retrofit2.http.QueryMap
import rx.Observable

/**
 * Created by cnbs5 on 2017/12/7.
 */
interface ApiStores {

    companion object {
        //baseUrl
//        val API_SERVER_URL = "http://120.79.58.87:8080/txcCar/"
//        val API_SERVER_URL = "http://192.168.100.222:8080/txcCar/"
        val API_SERVER_URL = "http://www.taoxianche.cn/txcCar/"
    }

    //验证码
    @GET("smsVerifyFront/send.html")
    fun sendCode(@QueryMap options: Map<String, String>): Observable<ApiResult>

    //登录
    @GET("userFront/login.html")
    fun login(@QueryMap options: Map<String, String>): Observable<ApiResult>
}
