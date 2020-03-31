package com.hr.unizg.fer.auris.permissions.management

interface ActivityPermissionResultHandler {
    fun handlePermissionResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
}