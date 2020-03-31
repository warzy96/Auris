package com.hr.unizg.fer.auris.permissions

import com.hr.unizg.fer.auris.base.BaseViewModel

abstract class PermissionViewModel : BaseViewModel() {

    abstract fun checkPermissions(requiredPermissions: Array<String>): Boolean

    abstract suspend fun requestPermissions(requiredPermissions: Array<String>, requestCodePermissions: Int)
}
