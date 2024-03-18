package io.lumyuan.abuilder.local

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

val LocalDarkLocal = compositionLocalOf<DarkTheme> { error("Not provided.") }

class DarkTheme(private val darkItem: DarkItem) {
    var type by mutableStateOf(darkItem)
}

enum class DarkItem {
    SYSTEM, LIGHT, DARK
}