package com.hr.unizg.fer.auris.permissions

class PermissionViewModelImpl(private val permissionHandler: PermissionHandler) : PermissionViewModel() {

    fun checkPermissions(permissionList: Array<String>): Boolean {
        return permissionHandler.allPermissionsGranted(permissionList)
    }

    fun requestPermissions(permissionList: Array<String>, requestCode: Int) {
        permissionHandler.requestPermissions(permissionList, requestCode)
    }
}