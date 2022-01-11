package com.mobile.weatherappdvt.util

import com.mobile.weatherappdvt.util.Constants.DEGREE_SYMBOL

object FormatUtils {

    fun getTempFormat(temp: String?): String? {
        if (temp == null) {
            return null
        }

        return temp + DEGREE_SYMBOL
    }
}
