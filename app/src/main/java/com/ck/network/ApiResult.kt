package com.ck.network

/**
 * Created by cnbs5 on 2017/12/7.
 */

data class ApiResult constructor(val baseResponse: BaseResponse) {

    data class BaseResponse(
            val code: Int = 0,
            val msg: String? = null
    )
}