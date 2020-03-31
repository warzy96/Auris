package com.hr.unizg.fer.auris.permissions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.hr.unizg.fer.auris.R
import com.hr.unizg.fer.auris.base.BaseFragment
import com.hr.unizg.fer.auris.permissions.management.PERMISSION_CAMERA
import com.hr.unizg.fer.auris.permissions.management.REQUEST_CODE_PERMISSIONS
import kotlinx.android.synthetic.main.fragment_permissions.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PermissionsFragment : BaseFragment<PermissionViewModel>() {

    override val viewModel: PermissionViewModel by viewModel()

    companion object {
        const val TAG = "PermissionsFragment"
        fun newInstance(): Fragment {
            return PermissionsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_permissions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestPermissionsButton.setOnClickListener {
            lifecycleScope.launch {
                viewModel.requestPermissions(
                    arrayOf(PERMISSION_CAMERA),
                    REQUEST_CODE_PERMISSIONS
                )
            }
        }
    }

}