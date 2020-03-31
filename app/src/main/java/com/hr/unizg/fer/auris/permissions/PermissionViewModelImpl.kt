package com.hr.unizg.fer.auris.permissions

import com.hr.unizg.fer.auris.permissions.management.PermissionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class PermissionViewModelImpl(private val permissionHandler: PermissionHandler) : PermissionViewModel() {

    override fun checkPermissions(requiredPermissions: Array<String>): Boolean {
        return permissionHandler.allPermissionsGranted(requiredPermissions)
    }

    override suspend fun requestPermissions(requiredPermissions: Array<String>, requestCodePermissions: Int) {
        permissionHandler.requestPermissions(requiredPermissions, requestCodePermissions)
    }
}