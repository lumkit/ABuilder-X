package io.lumyuan.abuilder.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

/**
 * 项目模板列表项
 * 待实现单选功能
 */
@Composable
fun ProjectTemplateItem(
    modifier: Modifier,
    previewer: @Composable () -> Unit,
    label: @Composable () -> Unit,
    onClick: () -> Unit
) {
    val density = LocalDensity.current
    var heightDp by remember { mutableStateOf(0.dp) }
    Column(
        modifier = modifier
            .onGloballyPositioned {
                val width = it.size.width
                // 计算预览器高度
                heightDp = with(density) {
                    (width.toFloat() * (16f / 9f)).toDp()
                }
            }
            .clip(RoundedCornerShape(4.dp))
            .background(color = MaterialTheme.colorScheme.onBackground.copy(alpha = .05f))
            .clickable { onClick() },
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(heightDp),
            contentAlignment = Alignment.Center
        ) {
            previewer()
        }
        Spacer(modifier = Modifier.size(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            ProvideTextStyle(value = MaterialTheme.typography.bodyMedium) {
                label()
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
    }
}