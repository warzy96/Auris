package com.hr.unizg.fer.auris

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.hr.unizg.fer.auris.base.BaseActivity
import com.hr.unizg.fer.auris.di.MAIN_ACTIVITY_SCOPE_ID
import com.hr.unizg.fer.auris.navigation.Router
import com.hr.unizg.fer.auris.permissions.management.PERMISSION_CAMERA
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class MainActivity : BaseActivity() {

    private val router: Router by getKoin()
        .getOrCreateScope(MAIN_ACTIVITY_SCOPE_ID, named<MainActivity>())
        .inject { parametersOf(supportFragmentManager) }

    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        subscribeToPermissionRequestEvents()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        viewModel.setOnRequestPermissionResultListener {
            if (it) router.showCameraFragments()
            else router.showPermissionsFragment()
        }

        viewModel.checkPermissionsGranted(arrayOf(PERMISSION_CAMERA))
    }

    override fun onPause() {
        super.onPause()
        getKoin().getScope(MAIN_ACTIVITY_SCOPE_ID).close()
        viewModel.removeOnRequestPermissionResultListener()
    }

    private fun subscribeToPermissionRequestEvents() {
        lifecycleScope.launch {
            viewModel.providePermissionRequestEventFlow().collect {
                requestPermissions(it.permissionList, it.requestCode)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        viewModel.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}

