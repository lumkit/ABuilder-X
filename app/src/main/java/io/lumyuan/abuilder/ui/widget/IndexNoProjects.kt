package io.lumyuan.abuilder.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FolderOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import io.lumyuan.abuilder.R
import io.lumyuan.abuilder.ui.pages.LocalScreenNavHostController
import io.lumyuan.abuilder.ui.pages.ScreenRoute

@Composable
fun IndexNoProjects() {
    val navHostController = LocalScreenNavHostController.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.FolderOff,
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = stringResource(id = R.string.text_no_open_projects),
            style = MaterialTheme.typography.bodyMedium.copy(textDecoration = TextDecoration.Underline),
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .clickable {
                    navHostController.navigate(ScreenRoute.NEW_PROJECT)
                }
                .padding(2.dp)
        )
    }
}