package com.ck.network

import com.ck.bean.Version

/**
 * Created by cnbs5 on 2017/12/7.
 */

//data class BaseResponse constructor(val code:Int,val msg:String)(
//        val code: Int = 0
//        val msg: String? = null
//)

data class ApiResult(var code: Int,var msg:String)

data class VersionResult(var code: Int,var msg:String,var version : Version)

