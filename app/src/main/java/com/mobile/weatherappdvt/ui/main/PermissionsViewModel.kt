package com.mobile.weatherappdvt.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PermissionsViewModel : ViewModel() {

    val locationPermissionsRequested = MutableLiveData<Boolean>()

    val locationPermissionsGranted = MutableLiveData<Boolean>()

    fun requestLocationPermissions(request : Boolean) {
        locationPermissionsRequested.value = request
    }

    fun permissionsGranted(granted: Boolean) {
        locationPermissionsGranted.value = granted
    }
}