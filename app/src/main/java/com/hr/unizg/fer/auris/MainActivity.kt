package com.hr.unizg.fer.auris

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hr.unizg.fer.auris.navigation.Router
import com.hr.unizg.fer.auris.permissions.PermissionHandler
import com.hr.unizg.fer.auris.permissions.REQUEST_CODE_PERMISSIONS
import com.hr.unizg.fer.auris.permissions.REQUIRED_PERMISSIONS
import org.koin.core.parameter.parametersOf
import org.koin.ext.getOrCreateScope

class MainActivity : AppCompatActivity() {

    private val permissionHandler: PermissionHandler by getOrCreateScope().inject { parametersOf(this) }

    private val router: Router by getOrCreateScope().inject { parametersOf(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        permissionHandler.requestPermissions(REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (permissionHandler.allPermissionsGranted(permissions)) {
                router.showCameraFragments()
            } else {
                router.showPermissionsFragment()
            }
        }
    }
}

