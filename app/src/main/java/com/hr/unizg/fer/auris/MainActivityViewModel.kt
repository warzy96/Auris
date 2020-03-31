package com.hr.unizg.fer.auris

import com.hr.unizg.fer.auris.navigation.RouterAction
import com.hr.unizg.fer.auris.permissions.management.PermissionBaseViewModel
import com.hr.unizg.fer.auris.permissions.management.PermissionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
abstract class MainActivityViewModel(permissionHandler: PermissionHandler) : PermissionBaseViewModel(permissionHandler) {

    abstract fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray)

    abstract fun provideRouterEventFlow(): Flow<RouterAction>
}