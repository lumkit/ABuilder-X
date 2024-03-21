package io.lumyuan.abuilder.pojo

import android.net.Uri
import java.io.Serializable

/**
 * 首页项目列表项
 */
data class IndexProject(
    val path: Uri,
    val name: String,
    val enabled: Boolean,
    val lastModified: Long = System.currentTimeMillis()
): Serializable