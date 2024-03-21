package io.lumyuan.abuilder.ui.pages.welcome

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import io.lumyuan.abuilder.R
import io.lumyuan.abuilder.common.SharedPreferencesUtil
import io.lumyuan.abuilder.local.Const
import io.lumyuan.abuilder.ui.pages.LocalScreenNavHostController
import io.lumyuan.abuilder.ui.pages.ScreenRoute
import io.lumyuan.abuilder.ui.components.Item
import io.lumyuan.abuilder.ui.widget.Logo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen() {
    val viewModel = viewModel<WelcomeViewModel>()
    val navHostController = LocalScreenNavHostController.current

    val navPositionState by viewModel.navPosition.collectAsStateWithLifecycle()
    val permissionStorage by viewModel.permissionStorage.collectAsStateWithLifecycle()

    BackHandler(navPositionState > 0) {
        viewModel.navTo(navPositionState - 1)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                AnimatedContent(
                    targetState = navPositionState,
                    label = "welcome navigation animate",
                    transitionSpec = { fadeIn().togetherWith(fadeOut()) }
                ) { position ->
                    when (position) {
                        0 -> Welcome()
                        1 -> Statistics()
                        2 -> Permissions()
                        3 -> RuntimeInstall()
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                this@Column.AnimatedVisibility(
                    modifier = Modifier.align(Alignment.CenterStart),
                    visible = navPositionState > 0,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    NavButton(
                        modifier = Modifier.align(Alignment.CenterStart),
                        navDirection = NavDirection.START
                    ) {
                        viewModel.navTo(navPositionState - 1)
                    }
                }

                val progress by animateFloatAsState(
                    targetValue = (navPositionState.toFloat() + 1f) / 4f,
                    animationSpec = tween(),
                    label = "progress"
                )

                LinearProgressIndicator(
                    modifier = Modifier
                        .width(64.dp)
                        .align(Alignment.Center),
                    progress = progress
                )

                AnimatedContent(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    targetState = navPositionState < 3,
                    label = "next button animate",
                    transitionSpec = { fadeIn().togetherWith(fadeOut()) }
                ) {isLast ->
                    if (isLast) {
                        NavButton(
                            navDirection = NavDirection.END,
                            enabled = if (navPositionState == 2) {
                                permissionStorage
                            } else {
                                true
                            }
                        ) {
                            viewModel.navTo(navPositionState + 1)
                        }
                    } else {
                        Card(
                            shape = CircleShape,
                            onClick = {
                                SharedPreferencesUtil.save(Const.INITIALIZATION, "true")
//                                navHostController.navigate(ScreenRoute.TERMINAL)
                                navHostController.navigate(ScreenRoute.INDEX)
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Done,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .size(24.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

private enum class NavDirection {
    START, END
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NavButton(
    modifier: Modifier = Modifier,
    navDirection: NavDirection,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = CircleShape,
        onClick = onClick,
        enabled = enabled
    ) {
        Icon(
            imageVector = when (navDirection) {
                NavDirection.START -> Icons.AutoMirrored.Filled.KeyboardArrowLeft
                NavDirection.END -> Icons.AutoMirrored.Filled.KeyboardArrowRight
            },
            contentDescription = null,
            modifier = Modifier
                .padding(16.dp)
                .size(24.dp)
        )
    }
}

@Composable
private fun Welcome() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo(modifier = Modifier.size(82.dp))
        Spacer(modifier = Modifier.size(32.dp))
        Text(
            text = stringResource(id = R.string.text_welcome),
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = stringResource(id = R.string.text_welcome_tips),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Statistics() {
    var agreeStatistics by rememberSaveable {
        mutableStateOf(SharedPreferencesUtil.load(Const.AGREE_STATISTICS) != "false")
    }

    val coroutineScope = rememberCoroutineScope { Dispatchers.IO }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = {
                Column {
                    Text(
                        text = stringResource(id = R.string.text_statistics_title),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = stringResource(id = R.string.text_statistics_subtitle),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        )
        Spacer(modifier = Modifier.size(16.dp))
        Item(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            leadingIcon = {
                Logo(modifier = Modifier.fillMaxSize())
            },
            label = {
                Text(text = stringResource(id = R.string.text_statistics_abuilder))
            },
            trailingIcon = {
                var enabled by rememberSaveable { mutableStateOf(true) }
                Switch(
                    checked = agreeStatistics,
                    onCheckedChange = {
                        agreeStatistics = it
                        coroutineScope.launch {
                            enabled = false
                            SharedPreferencesUtil.save(Const.AGREE_STATISTICS, it.toString())
                            enabled = true
                        }
                    },
                    enabled = enabled
                )
            }
        )
        Text(
            text = stringResource(id = R.string.text_statistics_abuilder_tips),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Permissions() {
    val activity = LocalContext.current as ComponentActivity
    val viewModel = viewModel<WelcomeViewModel>()
    val storagePermissionState by viewModel.permissionStorage.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.setPermissionStorageState(
            XXPermissions.isGranted(
                activity,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    Permission.MANAGE_EXTERNAL_STORAGE
                } else {
                    Permission.WRITE_EXTERNAL_STORAGE
                }
            )
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = {
                Column {
                    Text(
                        text = stringResource(id = R.string.text_permissions_title),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = stringResource(id = R.string.text_permissions_subtitle),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        )
        Spacer(modifier = Modifier.size(16.dp))
        Item(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Folder, contentDescription = null)
            },
            label = {
                Text(text = stringResource(id = R.string.text_storage))
            },
            tips = {
                Text(text = stringResource(id = R.string.text_storage_tips))
            },
            trailingIcon = {
                AnimatedContent(
                    targetState = storagePermissionState,
                    label = "storage animate",
                    transitionSpec = { fadeIn().togetherWith(fadeOut()) }
                ) {
                    if (it) {
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                imageVector = Icons.Default.Done,
                                contentDescription = null,
                                tint = Color(0xFF269945)
                            )
                        }
                    } else {
                        TextButton(
                            onClick = {
                                viewModel.requestStoragePermission(activity)
                            },
                        ) {
                            Text(text = stringResource(id = R.string.authorize))
                        }
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RuntimeInstall() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = {
                Column {
                    Text(
                        text = stringResource(id = R.string.text_runtime_install_title),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = stringResource(id = R.string.text_runtime_install_subtitle),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        )
        Text(
            text = stringResource(id = R.string.text_runtime_install_none),
            modifier = Modifier.padding(16.dp)
        )
    }
}