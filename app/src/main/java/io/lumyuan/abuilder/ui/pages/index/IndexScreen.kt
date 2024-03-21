package io.lumyuan.abuilder.ui.pages.index

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AssistChip
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import io.lumyuan.abuilder.R
import io.lumyuan.abuilder.ui.components.MaterialScreen
import io.lumyuan.abuilder.ui.pages.LocalScreenNavHostController
import io.lumyuan.abuilder.ui.pages.ScreenRoute
import io.lumyuan.abuilder.ui.widget.IndexNoProjects
import io.lumyuan.abuilder.ui.widget.Logo
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun IndexScreen() {
    val activity = LocalContext.current as ComponentActivity
    val viewModel = viewModel<IndexViewModel>()

    //拦截返回事件
    BackHandler {
        activity.finish()
    }

    MaterialScreen(
        modifier = Modifier.fillMaxSize(),
        navigationIcon = {
            IconButton(onClick = {}) {
                Logo(modifier = Modifier.size(24.dp))
            }
        },
        title = {
            Text(text = stringResource(id = R.string.text_welcome_to_abuilder_x))
        },
        actions = {
            IndexActions()
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.size(8.dp))
            IndexTopActions()
            Spacer(modifier = Modifier.size(8.dp))
            IndexSearchProjects(viewModel)
            Spacer(modifier = Modifier.size(8.dp))
            IndexProjects(viewModel)
        }
    }
}

/**
 * 首页的右上角菜单
 */
@Composable
private fun IndexActions() {
    val navHostController = LocalScreenNavHostController.current
    var visable by rememberSaveable { mutableStateOf(false) }
    Column {
        IconButton(
            onClick = {
                visable = true
            }
        ) {
            Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
        }
    }
    DropdownMenu(expanded = visable, onDismissRequest = { visable = false }) {
        DropdownMenuItem(
            leadingIcon = {
                Icon(imageVector = Icons.Outlined.Settings, contentDescription = null)
            },
            text = {
                Text(text = stringResource(id = R.string.text_settings))
            },
            onClick = {
                navHostController.navigate(ScreenRoute.SETTINGS)
                visable = false
            }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun IndexTopActions() {
    val navHostController = LocalScreenNavHostController.current
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AssistChip(
            label = {
                Text(text = stringResource(id = R.string.text_new_project))
            },
            onClick = {
                navHostController.navigate(ScreenRoute.NEW_PROJECT)
            }
        )
        AssistChip(
            label = {
                Text(text = stringResource(id = R.string.text_open_project))
            },
            onClick = {
                // TODO 跳转打开项目页面

            }
        )
        AssistChip(
            label = {
                Text(text = stringResource(id = R.string.text_clone_git_repository))
            },
            onClick = {
                // TODO 弹出克隆Git仓库窗口

            }
        )
    }
}

@Composable
private fun IndexSearchProjects(viewModel: IndexViewModel) {
    val projectsFilterState by viewModel.projectsFilterState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            label = {
                Text(text = stringResource(id = R.string.text_search_projects))
            },
            value = projectsFilterState,
            onValueChange = {
                viewModel.setFilter(it)
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                )
            },
            trailingIcon = {
                Row(
                    horizontalArrangement = Arrangement.End
                ) {
                    AnimatedVisibility(
                        visible = projectsFilterState.isNotEmpty(),
                        label = "clean button animate",
                    ) {
                        IconButton(
                            onClick = {
                                viewModel.setFilter("")
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = null)
                        }
                    }
                }
            },
            singleLine = true,
        )
    }
}

@Composable
private fun IndexProjects(viewModel: IndexViewModel) {
    val projectsFilterState = viewModel.projectsFilterState.collectAsStateWithLifecycle()
    val projectsState by viewModel.projectsState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = projectsFilterState) {
        snapshotFlow { projectsFilterState.value }
            .onEach {
                viewModel.searchProjects(it)
            }.launchIn(this)
    }

    AnimatedContent(
        modifier = Modifier.fillMaxSize(),
        targetState = projectsState.isNotEmpty(),
        label = "index projects list animate"
    ) {
        if (it) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(projectsState) {

                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                IndexNoProjects()
            }
        }
    }
}