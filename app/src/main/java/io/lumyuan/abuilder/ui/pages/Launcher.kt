package io.lumyuan.abuilder.ui.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.lumyuan.abuilder.common.SharedPreferencesUtil
import io.lumyuan.abuilder.local.Const
import io.lumyuan.abuilder.ui.pages.index.IndexScreen
import io.lumyuan.abuilder.ui.pages.welcome.WelcomeScreen

val LocalScreenNavHostController = compositionLocalOf<NavHostController> { error("Not provided.") }

@Composable
fun Launcher() {
    val navHostController = rememberNavController()

    CompositionLocalProvider(
        LocalScreenNavHostController provides navHostController
    ) {
        val startRout = if (SharedPreferencesUtil.load(Const.INITIALIZATION) == null) {
            ScreenRoute.WELCOME
        } else {
            ScreenRoute.INDEX
        }

        NavHost(navController = navHostController, startDestination = startRout) {
            composable(ScreenRoute.WELCOME) {
                WelcomeScreen()
            }
            composable(ScreenRoute.INDEX) {
                IndexScreen()
            }
            composable(ScreenRoute.TERMINAL) {

            }
        }
    }
}