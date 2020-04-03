package com.hr.unizg.fer.auris

import com.hr.unizg.fer.auris.permissions.management.PermissionHandler
import com.hr.unizg.fer.auris.permissions.management.PermissionModel
import com.hr.unizg.fer.auris.permissions.management.REQUEST_CODE_PERMISSIONS
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@FlowPreview
class MainActivityViewModelImpl(permissionHandler: PermissionHandler) : MainActivityViewModel(permissionHandler) {

    private var onRequestPermissionResultListener: (Boolean) -> Unit = {}

    override fun setOnRequestPermissionResultListener(listener: (Boolean) -> Unit) {
        this.onRequestPermissionResultListener = listener
    }

    override fun removeOnRequestPermissionResultListener() {
        onRequestPermissionResultListener = {}
    }

    override fun providePermissionRequestEventFlow(): Flow<PermissionModel> = permissionHandler.permissionRequestChannel.asFlow()

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            checkPermissionsGranted(permissions)
        }
    }

    override fun checkPermissionsGranted(permissions: Array<String>) =
        onRequestPermissionResultListener(permissionHandler.allPermissionsGranted(permissions))
}
