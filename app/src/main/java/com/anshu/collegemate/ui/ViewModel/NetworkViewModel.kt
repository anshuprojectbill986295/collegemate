package com.anshu.collegemate.ui.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.anshu.collegemate.Utils.NetworkObserver
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class NetworkViewModel(application: Application) : AndroidViewModel(application) {
    private val networkObserver = NetworkObserver(application)

    val isOnline: StateFlow<Boolean> = networkObserver.connectivityFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = true // Assume online initially to avoid flickering on start if possible, or use current state
        )
}
