package com.mobile.weatherappdvt.ui.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel

class UiViewModel : ViewModel() {

    val uiState = MediatorLiveData<UiState>()

    fun setNoLocation() {
        setUiState(UiState.NO_LOCATION_FOUND)
    }

    fun addLoadingSource(isLoading: LiveData<Boolean>) {
        uiState.addSource(isLoading) {
            if(it) {
                setUiState(UiState.LOADING)
            }
        }
    }

    fun addErrorSource(errorMessage: LiveData<String?>) {
        uiState.addSource(errorMessage) {
            if (it != null) {
                setUiState(UiState.ERROR_MESSAGE_RECEIVED)
            } else {
                setUiState(UiState.SUCCESSFULLY_RETRIEVED_DATA)
            }
        }
    }

    private fun setUiState(state: UiState) {
        uiState.value = state
    }

    enum class UiState {
        LOADING, ERROR_MESSAGE_RECEIVED, SUCCESSFULLY_RETRIEVED_DATA, NO_LOCATION_FOUND
    }
}