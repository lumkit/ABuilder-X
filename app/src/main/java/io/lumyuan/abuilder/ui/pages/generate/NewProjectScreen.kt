package io.lumyuan.abuilder.ui.pages.generate

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import io.lumyuan.abuilder.R
import io.lumyuan.abuilder.ui.components.MaterialScreen

@Composable
fun NewProjectScreen() {
    val viewModel = viewModel<NewProjectViewModel>()
    val pageSelectIndexState = viewModel.pageSelectIndexState.collectAsStateWithLifecycle()
    MaterialScreen(
        modifier = Modifier.fillMaxSize(),
        title = {
            Text(text = stringResource(id = R.string.text_new_project))
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Spacer(modifier = Modifier.size(8.dp))
            Tabs(pageSelectIndexState) { viewModel.setCurrentPosition(it) }
            NewProjectContent(pageSelectIndexState)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {

                    }
                ) {
                    Text(text = stringResource(id = R.string.text_next))
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun Tabs(pageSelectIndexState: State<Int>, onSelect: (Int) -> Unit) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        NewProjectViewModel.projectTypes.forEachIndexed { index, id ->
            FilterChip(
                selected = index == pageSelectIndexState.value,
                onClick = {
                    onSelect(index)
                },
                label = {
                    Text(text = stringResource(id = id))
                }
            )
        }
    }
}

@Composable
private fun ColumnScope.NewProjectContent(pageSelectIndexState: State<Int>) {
    AnimatedContent(
        modifier = Modifier
            .fillMaxSize()
            .weight(1f),
        targetState = pageSelectIndexState.value,
        label = "project animate",
        transitionSpec = { (fadeIn() + slideInHorizontally()).togetherWith(slideOutHorizontally() + fadeOut()) }
    ) {
        when (it) {
            0 -> WebProjectScreen()
            1 -> WebAppScreen()
        }
    }
}