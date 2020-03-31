package com.hr.unizg.fer.auris.permissions.management

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel

const val REQUEST_CODE_PERMISSIONS = 10
const val PERMISSION_CAMERA = Manifest.permission.CAMERA

@ExperimentalCoroutinesApi
class PermissionHandler(private val context: Context) : PermissionSource {

    val permissionRequestChannel: BroadcastChannel<PermissionModel> = BroadcastChannel(1)

    override fun allPermissionsGranted(permissionList: Array<String>) = permissionList.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    override suspend fun requestPermissions(permissionList: Array<String>, requestCode: Int) {
        permissionRequestChannel.send(PermissionModel(permissionList, requestCode))
    }

    override fun handlePermissionResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

    }
}