package io.lumyuan.abuilder.ui.theme

import android.os.Build
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.gyf.immersionbar.ImmersionBar
import io.lumyuan.abuilder.local.DarkItem
import io.lumyuan.abuilder.local.DarkTheme
import io.lumyuan.abuilder.local.DynamicColorLocal
import io.lumyuan.abuilder.local.LocalDarkLocal
import io.lumyuan.abuilder.local.LocalDynamicColor

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
)

@Composable
fun ABuilderXTheme(
    content: @Composable () -> Unit
) {
    val systemInDarkTheme = isSystemInDarkTheme()

    val darkThemeState = remember { DarkTheme(DarkItem.SYSTEM) }
    val enabledDynamicColorState = remember { DynamicColorLocal(true) }

    val isDark = when (darkThemeState.type) {
        DarkItem.SYSTEM -> systemInDarkTheme
        DarkItem.LIGHT -> false
        DarkItem.DARK -> true
    }

    ImmersionBar.with(LocalContext.current as ComponentActivity)
        .transparentStatusBar()
        .transparentNavigationBar()
        .navigationBarDarkIcon(!isDark)
        .statusBarDarkFont(!isDark)
        .keyboardEnable(true)
        .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        .init()

    CompositionLocalProvider(
        LocalDarkLocal provides darkThemeState,
        LocalDynamicColor provides enabledDynamicColorState,
    ) {
        val colorScheme = when {
            enabledDynamicColorState.enabledDynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                when (darkThemeState.type) {
                    DarkItem.SYSTEM -> {
                        if (systemInDarkTheme) dynamicDarkColorScheme(context)
                        else dynamicLightColorScheme(context)
                    }

                    DarkItem.LIGHT -> dynamicLightColorScheme(context)
                    DarkItem.DARK -> dynamicDarkColorScheme(context)
                }
            }

            darkThemeState.type == DarkItem.SYSTEM -> {
                if (systemInDarkTheme) darkScheme else lightScheme
            }

            darkThemeState.type == DarkItem.DARK -> darkScheme

            else -> lightScheme
        }

        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

