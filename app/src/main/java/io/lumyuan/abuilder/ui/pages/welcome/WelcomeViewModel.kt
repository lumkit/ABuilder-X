package io.lumyuan.abuilder.ui.pages.welcome

import android.app.Activity
import android.os.Build
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import io.lumyuan.abuilder.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class WelcomeViewModel: ViewModel() {

    private val _navPosition = MutableStateFlow(0)
    val navPosition = _navPosition.asStateFlow()

    private val _permissionStorage = MutableStateFlow(false)
    val permissionStorage = _permissionStorage.asStateFlow()

    fun navTo(position: Int) {
        if (position < 0 || position >= 4)
            return
        _navPosition.value = position
    }

    fun requestStoragePermission(activity: Activity) {
        XXPermissions.with(activity)
            .permission(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    Permission.MANAGE_EXTERNAL_STORAGE
                } else {
                    Permission.WRITE_EXTERNAL_STORAGE
                }
            ).request(object : OnPermissionCallback {
                override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                    _permissionStorage.value = allGranted
                }

                override fun onDenied(permissions: MutableList<String>, doNotAskAgain: Boolean) {
                    super.onDenied(permissions, doNotAskAgain)
                    _permissionStorage.value = false
                    Toast.makeText(activity, R.string.toast_storage_permission_denied, Toast.LENGTH_SHORT).show()
                }
            })
    }

    fun setPermissionStorageState(granted: Boolean) {
        _permissionStorage.value = granted
    }

}