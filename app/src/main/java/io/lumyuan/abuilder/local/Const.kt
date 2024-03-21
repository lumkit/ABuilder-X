package io.lumyuan.abuilder.local

import android.os.Environment
import java.io.File

object Const {
    const val INITIALIZATION = "initialization"
    const val AGREE_STATISTICS = "agree statistics"

    const val PROJECTS_ROOT = "projects root"

    //默认的项目存放目录
    val PROJECTS_ROOT_DEFAULT by lazy {
        val file = File(Environment.getExternalStorageDirectory(), "Documents/ABuilderXProjects")
        if (!file.exists()) {
            file.mkdirs()
        }
        file.absolutePath
    }
}