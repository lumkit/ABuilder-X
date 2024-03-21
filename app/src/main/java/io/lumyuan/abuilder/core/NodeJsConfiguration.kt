package io.lumyuan.abuilder.core

import io.lumyuan.abuilder.annotate.Temporary
import io.lumyuan.filescope.core.shell.KeepShellPublic

@Temporary
class NodeJsConfiguration {
    private val versions = arrayOf(
        "v21.7.1",
        "v20.11.1",
        "v16.20.2",
    )
    private fun downloadUrl(versionTag: String) = "https://nodejs.org/download/release/$versionTag/node-$versionTag-linux-arm64.tar.gz"

    fun isInstalled() {
        val doCmdSync = KeepShellPublic.doCmdSync(
            "ls /data/data/io.lumyuan.abuilder/files/node-v16.20.2-linux-arm64/bin/node"
        )
        println("result: $doCmdSync")
    }
}