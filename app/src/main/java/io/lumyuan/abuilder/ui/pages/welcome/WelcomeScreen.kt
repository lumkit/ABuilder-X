package io.lumyuan.abuilder.ui.pages.welcome

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
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.lumyuan.abuilder.R
import io.lumyuan.abuilder.ui.pages.LocalScreenNavHostController
import io.lumyuan.abuilder.ui.pages.ScreenRoute
import io.lumyuan.abuilder.ui.widget.Logo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen() {
    val viewModel = viewModel<WelcomeViewModel>()
    val navHostController = LocalScreenNavHostController.current

    val navPositionState by viewModel.navPosition.collectAsState()

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
                        .width(56.dp)
                        .align(Alignment.Center),
                    progress = progress
                )
                this@Column.AnimatedVisibility(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    visible = navPositionState < 3,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    NavButton(
                        navDirection = NavDirection.END
                    ) {
                        viewModel.navTo(navPositionState + 1)
                    }
                }
                this@Column.AnimatedVisibility(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    visible = navPositionState == 3,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Card(
                        shape = CircleShape,
                        onClick = {
                            navHostController.navigate(ScreenRoute.TERMINAL)
                        }
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

private enum class NavDirection {
    START, END
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NavButton(
    modifier: Modifier = Modifier,
    navDirection: NavDirection,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = CircleShape,
        onClick = onClick
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

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Permissions() {
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

    }
}