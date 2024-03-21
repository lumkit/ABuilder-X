package io.lumyuan.abuilder.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.lumyuan.abuilder.ui.widget.Logo

@Preview
@Composable
private fun PreviewItem() {
    Item(
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = {
            Logo(modifier = Modifier.fillMaxSize())
        },
        label = {
            Text(text = "This is a item bar.")
        },
        tips = {
            Text(text = "this is a item tips")
        },
        trailingIcon = {
            Switch(checked = false, onCheckedChange = null)
        }
    )
}

@Composable
fun Item(
    modifier: Modifier,
    shape: Shape = RoundedCornerShape(8.dp),
    border: BorderStroke = BorderStroke(.5.dp, DividerDefaults.color),
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    label: @Composable () -> Unit,
    tips: @Composable (() -> Unit)? = null,
) {
    OutlinedCard(
        modifier = modifier,
        shape = shape,
        border = border
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon?.also {
                Spacer(modifier = Modifier.size(16.dp))
                Box(
                    modifier = Modifier.size(24.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    it()
                }
            }
            Spacer(modifier = Modifier.size(16.dp))
            Box(
                modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
                contentAlignment = Alignment.CenterStart
            ) {
                Column {
                    ProvideTextStyle(value = MaterialTheme.typography.bodyLarge) {
                        Text(text = "")
                    }
                    ProvideTextStyle(value = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.outline)) {
                        Text(text = "")
                    }
                }
                Column {
                    ProvideTextStyle(value = MaterialTheme.typography.bodyLarge) {
                        label()
                    }
                    ProvideTextStyle(value = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.outline)) {
                        tips?.invoke()
                    }
                }
            }
            Spacer(modifier = Modifier.size(16.dp))
            trailingIcon?.also {
                it()
                Spacer(modifier = Modifier.size(16.dp))
            }
        }
    }
}