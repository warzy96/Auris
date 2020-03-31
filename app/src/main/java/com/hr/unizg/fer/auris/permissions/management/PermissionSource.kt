package com.hr.unizg.fer.auris.permissions.management

interface PermissionSource : ActivityPermissionResultHandler {

    suspend fun requestPermissions(permissionList: Array<String>, requestCode: Int)

    fun allPermissionsGranted(permissionList: Array<String>): Boolean
}