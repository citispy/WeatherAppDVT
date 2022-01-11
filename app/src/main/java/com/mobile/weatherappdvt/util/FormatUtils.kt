package com.mobile.weatherappdvt.util

import android.content.Context
import android.location.Geocoder
import android.location.Location
import com.mobile.weatherappdvt.util.Constants.DEGREE_SYMBOL
import java.lang.Exception
import java.lang.StringBuilder
import java.util.*

object FormatUtils {

    fun getTempFormat(temp: String?): String? {
        if (temp == null) {
            return null
        }

        return temp + DEGREE_SYMBOL
    }

    fun descriptionForLocation(location: Location?, context: Context): String? {
        var description: String? = null
        if (location != null) {
            val geocoder = Geocoder(context, Locale.getDefault())
            try {
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                if (addresses.size > 0) {
                    val address = addresses[0]
                    val descBuilder = StringBuilder("")

                    // City
                    if (address.locality != null) {
                        descBuilder.append(address.locality).append(", ")
                    }

                    // remove trailing comma
                    val length = descBuilder.length
                    if (length > 0) {
                        descBuilder.delete(length - 2, length)
                    }
                    description = descBuilder.toString()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return description
    }
}
