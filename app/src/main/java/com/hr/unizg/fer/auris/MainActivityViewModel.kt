package com.hr.unizg.fer.auris

import com.hr.unizg.fer.auris.permissions.management.PermissionBaseViewModel
import com.hr.unizg.fer.auris.permissions.management.PermissionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
abstract class MainActivityViewModel(permissionHandler: PermissionHandler) : PermissionBaseViewModel(permissionHandler) {

    abstract fun setOnRequestPermissionResultListener(listener: (Boolean) -> Unit)

    abstract fun removeOnRequestPermissionResultListener()

    abstract fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray)
}
