package com.hr.unizg.fer.auris

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.hr.unizg.fer.auris.base.BaseActivity
import com.hr.unizg.fer.auris.navigation.Router
import com.hr.unizg.fer.auris.permissions.management.PERMISSION_CAMERA
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.ext.getOrCreateScope

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class MainActivity : BaseActivity() {

    private val router: Router by getOrCreateScope().inject { parametersOf(supportFragmentManager) }

    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        subscribeToRoutingEvents()

        subscribeToPermissionRequestEvents()

        viewModel.checkPermissionsGranted(arrayOf(PERMISSION_CAMERA))
    }

    private fun subscribeToPermissionRequestEvents() {
        lifecycleScope.launch {
            viewModel.providePermissionRequestEventFlow().collect {
                requestPermissions(it.permissionList, it.requestCode)
            }
        }
    }

    private fun subscribeToRoutingEvents() {
        lifecycleScope.launch {
            viewModel.provideRouterEventFlow().collect {
                router.processAction(it)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        viewModel.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}

