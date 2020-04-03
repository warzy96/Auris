package com.hr.unizg.fer.auris.permissions.management

import com.hr.unizg.fer.auris.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
abstract class PermissionBaseViewModel(protected val permissionHandler: PermissionHandler) : BaseViewModel() {

    abstract fun checkPermissionsGranted(permissions: Array<String>)

    abstract fun providePermissionRequestEventFlow(): Flow<PermissionModel>
}
