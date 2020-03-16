package com.hr.unizg.fer.auris.permissions

class PermissionViewModel(private val permissionHandler: PermissionHandler) : PermissionContract.ViewModel() {

    fun checkPermissions(permissionList: Array<String>): Boolean {
        return permissionHandler.allPermissionsGranted(permissionList)
    }

    fun requestPermissions(permissionList: Array<String>, requestCode: Int) {
        permissionHandler.requestPermissions(permissionList, requestCode)
    }
}