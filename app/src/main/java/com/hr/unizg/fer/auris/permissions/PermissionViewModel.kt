package com.hr.unizg.fer.auris.permissions

import com.hr.unizg.fer.auris.base.BaseViewModel

class PermissionViewModel(private val permissionHandler: PermissionHandler) : PermissionContract.ViewModel() {

    fun checkPermissions(permissionList: Array<String>): Boolean {
        return permissionHandler.allPermissionsGranted(permissionList)
    }

    fun requestPermissions(permissionList: Array<String>, requestCode: Int) {
        permissionHandler.requestPermissions(permissionList, requestCode)
    }
}