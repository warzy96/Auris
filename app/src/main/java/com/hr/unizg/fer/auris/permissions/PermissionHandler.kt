package com.hr.unizg.fer.auris.permissions

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

const val REQUEST_CODE_PERMISSIONS = 10
val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

class PermissionHandler(private val activity: AppCompatActivity) : ActivityCompat.OnRequestPermissionsResultCallback {

    fun allPermissionsGranted(permissionList: Array<String>) = permissionList.all {
        ContextCompat.checkSelfPermission(activity, it) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermissions(permissionList: Array<String>, requestCode: Int) {
        activity.requestPermissions(permissionList, requestCode)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

    }
}