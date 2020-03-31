package com.hr.unizg.fer.auris

import androidx.lifecycle.viewModelScope
import com.hr.unizg.fer.auris.navigation.OpenCameraFragments
import com.hr.unizg.fer.auris.navigation.OpenPermissionFragment
import com.hr.unizg.fer.auris.navigation.RouterAction
import com.hr.unizg.fer.auris.permissions.management.PermissionHandler
import com.hr.unizg.fer.auris.permissions.management.PermissionModel
import com.hr.unizg.fer.auris.permissions.management.REQUEST_CODE_PERMISSIONS
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@FlowPreview
class MainActivityViewModelImpl(permissionHandler: PermissionHandler) : MainActivityViewModel(permissionHandler) {

    private val routerEventChannel = BroadcastChannel<RouterAction>(1)
    override fun provideRouterEventFlow() = routerEventChannel.asFlow()

    override fun providePermissionRequestEventFlow(): Flow<PermissionModel> = permissionHandler.permissionRequestChannel.asFlow()

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            checkPermissionsGranted(permissions)
        }
    }

    override fun checkPermissionsGranted(permissions: Array<String>) {
        if (permissionHandler.allPermissionsGranted(permissions)) {
            sendRoutingAction(OpenCameraFragments)
        } else {
            sendRoutingAction(OpenPermissionFragment)
        }
    }

    private fun sendRoutingAction(action: RouterAction) {
        viewModelScope.launch { routerEventChannel.send(action) }
    }
}