package com.mobile.weatherappdvt.util

import android.Manifest
import android.content.Context
import pub.devrel.easypermissions.EasyPermissions

object TrackingUtils {

    fun hasLocationPermissions(context: Context): Boolean {
       return EasyPermissions.hasPermissions(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }
}