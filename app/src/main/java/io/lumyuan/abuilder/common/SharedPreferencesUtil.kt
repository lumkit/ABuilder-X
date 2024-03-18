package io.lumyuan.abuilder.common

import android.content.Context
import io.lumyuan.abuilder.ABuilderApplication

object SharedPreferencesUtil {

    private val sharedPreferences by lazy {
        ABuilderApplication.application.getSharedPreferences(ABuilderApplication.application.packageName, Context.MODE_PRIVATE)
    }

    fun save(key: Any?, value: String?): Boolean{
        return sharedPreferences.edit().putString(key.toString(), value).commit()
    }

    fun load(key: Any?): String?{
        return sharedPreferences.getString(key?.toString(), null)
    }

}