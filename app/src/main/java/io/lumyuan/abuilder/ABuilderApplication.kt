package io.lumyuan.abuilder

import android.app.Application

class ABuilderApplication: Application() {

    companion object{
        lateinit var application: ABuilderApplication
    }

    override fun onCreate() {
        super.onCreate()
        application = this

    }

}