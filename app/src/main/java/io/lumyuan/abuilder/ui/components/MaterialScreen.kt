package io.lumyuan.abuilder.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.lumyuan.abuilder.ui.pages.LocalScreenNavHostController

/**
 * 一个Material Design 3风格的统一样式屏幕脚手架
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialScreen(
    modifier: Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    title: @Composable () -> Unit,
    subtitle: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    val navHostController = LocalScreenNavHostController.current
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    if (navigationIcon == null) {
                        IconButton(
                            onClick = {
                                navHostController.popBackStack()
                            }
                        ) {
                            Icon(imageVector = Icons.Default.ChevronLeft, contentDescription = null)
                        }
                    } else {
                        navigationIcon()
                    }
                },
                title = {
                    if (subtitle == null) {
                        title()
                    } else {
                        Column {
                            ProvideTextStyle(value = MaterialTheme.typography.titleMedium) {
                                title()
                            }
                            ProvideTextStyle(value = MaterialTheme.typography.labelMedium) {
                                subtitle()
                            }
                        }
                    }
                },
                actions = actions
            )
        },
        content = content
    )
}