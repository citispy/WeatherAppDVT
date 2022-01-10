package com.mobile.weatherappdvt.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.weatherappdvt.util.Event

class PermissionsViewModel : ViewModel() {

    val locationPermissionsRequested = MutableLiveData<Event<Boolean>>()

    val locationPermissionsGranted = MutableLiveData<Boolean>()

    fun requestLocationPermissions() {
        locationPermissionsRequested.value = Event(true)
    }

    fun permissionsGranted(granted: Boolean) {
        locationPermissionsGranted.value = granted
    }
}