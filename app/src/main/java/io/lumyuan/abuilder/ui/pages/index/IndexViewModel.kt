package io.lumyuan.abuilder.ui.pages.index

import androidx.lifecycle.ViewModel
import io.lumyuan.abuilder.pojo.IndexProject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class IndexViewModel : ViewModel() {

    private val _projectsFilterState = MutableStateFlow("")
    val projectsFilterState = _projectsFilterState.asStateFlow()

    private val _projectsState = MutableStateFlow<Array<IndexProject>>(arrayOf())
    val projectsState = _projectsState.asStateFlow()


    fun setFilter(filter: String) {
        _projectsFilterState.value = filter
    }

    /**
     * 加载项目
     */
    fun loadProjects() {

    }

    /**
     * 搜索项目
     */
    fun searchProjects(filter: String) {
        val oldList = _projectsState.value
        _projectsState.value = oldList.filter {
            it.name.contains(filter) || it.path.toString().contains(filter)
        }.sortedBy {
            it.lastModified
        }.reversed()
            .toTypedArray()
    }

}