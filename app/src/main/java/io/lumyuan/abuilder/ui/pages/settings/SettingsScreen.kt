package io.lumyuan.abuilder.ui.pages.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.lumyuan.abuilder.R
import io.lumyuan.abuilder.ui.components.MaterialScreen

@Composable
fun SettingsScreen() {

    MaterialScreen(
        modifier = Modifier.fillMaxSize(),
        title = {
            Text(text = stringResource(id = R.string.text_settings))
        }
    ) {

    }
}