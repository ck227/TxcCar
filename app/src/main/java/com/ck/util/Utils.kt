package com.ck.util

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by cnbs5 on 2017/12/7.
 */
object Utils {

    fun isMobileNO(mobiles: String): Boolean {
        val p = Pattern.compile("^(1[3,4,5,7,8][0-9])\\d{8}$")
        val m: Matcher
        try {
            m = p.matcher(mobiles)
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
            return false
        }
        return m.matches()
    }
}