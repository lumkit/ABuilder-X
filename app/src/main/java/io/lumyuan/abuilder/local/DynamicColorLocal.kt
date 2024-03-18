package io.lumyuan.abuilder.local

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class DynamicColorLocal(private val isDynamicColor: Boolean) {
    var enabledDynamicColor by mutableStateOf(isDynamicColor)
}

val LocalDynamicColor = compositionLocalOf<DynamicColorLocal> { error("Not provided.") }