package io.lumyuan.abuilder.ui.pages.generate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.lumyuan.abuilder.ui.widget.ProjectTemplateItem

@Composable
fun WebProjectScreen() {

    LazyVerticalStaggeredGrid(
        modifier = Modifier.fillMaxSize(),
        columns = StaggeredGridCells.Fixed(3),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalItemSpacing = 8.dp
    ) {
        items(NewProjectViewModel.webTemplates) {
            ProjectTemplateItem(
                modifier = Modifier.fillMaxWidth(),
                previewer = it.previewer,
                label = it.label
            ) {
                when (it.style) {
                    NewProjectViewModel.ProjectTemplateStyle.EMPTY -> {
                        // TODO 创建空项目模板

                    }
                    NewProjectViewModel.ProjectTemplateStyle.SIMPLE -> {
                        // TODO 创建基础项目模板

                    }
                    NewProjectViewModel.ProjectTemplateStyle.VUE_2 -> {
                        // TODO 创建VUE2项目模板

                    }
                    NewProjectViewModel.ProjectTemplateStyle.VUE_3 -> {
                        // TODO 创建VUE3项目模板

                    }
                }
            }
        }
    }
}