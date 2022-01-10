package com.mobile.weatherappdvt.api

import androidx.lifecycle.MutableLiveData

open class ABaseRequestManager {

    val isLoading = MutableLiveData<Boolean>()

    fun setIsLoading(isLoading: Boolean) {
        this.isLoading.value = isLoading
    }
}