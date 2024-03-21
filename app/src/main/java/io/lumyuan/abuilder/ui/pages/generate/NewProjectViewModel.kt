package io.lumyuan.abuilder.ui.pages.generate

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import io.lumyuan.abuilder.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NewProjectViewModel : ViewModel() {

    enum class ProjectTemplateStyle {
        EMPTY, SIMPLE, VUE_2, VUE_3
    }

    data class ProjectTemplateItem(
        val style: ProjectTemplateStyle,
        val previewer: @Composable () -> Unit,
        val label: @Composable () -> Unit
    )

    companion object {
        val projectTypes = arrayOf<@receiver:StringRes Int>(
            R.string.text_project_web,
            R.string.text_project_webapp
        )

        val webTemplates = arrayOf(
            ProjectTemplateItem(
                style = ProjectTemplateStyle.EMPTY,
                previewer = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_emptysquare),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )
                },
                label = {
                    Text(text = stringResource(id = R.string.text_project_empty))
                }
            ),
            ProjectTemplateItem(
                style = ProjectTemplateStyle.EMPTY,
                previewer = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_h5_logo),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )
                },
                label = {
                    Text(text = stringResource(id = R.string.text_project_simple))
                }
            ),
            ProjectTemplateItem(
                style = ProjectTemplateStyle.EMPTY,
                previewer = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_vue_logo),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )
                },
                label = {
                    Text(text = stringResource(id = R.string.text_project_vue_2))
                }
            ),
            ProjectTemplateItem(
                style = ProjectTemplateStyle.EMPTY,
                previewer = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_vue_logo),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )
                },
                label = {
                    Text(text = stringResource(id = R.string.text_project_vue_3))
                }
            )
        )
    }

    private val _pageSelectIndexState = MutableStateFlow(0)
    val pageSelectIndexState = _pageSelectIndexState.asStateFlow()

    fun setCurrentPosition(position: Int) {
        _pageSelectIndexState.value = position
    }

}