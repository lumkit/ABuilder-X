package io.lumyuan.abuilder.ui.pages.welcome

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class WelcomeViewModel: ViewModel() {

    private val _navPosition = MutableStateFlow(0)
    val navPosition = _navPosition.asStateFlow()

    fun navTo(position: Int) {
        if (position < 0 || position >= 4)
            return
        _navPosition.value = position
    }

}