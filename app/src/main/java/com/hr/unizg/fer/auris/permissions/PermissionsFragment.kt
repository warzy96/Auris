package com.hr.unizg.fer.auris.permissions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hr.unizg.fer.auris.R
import com.hr.unizg.fer.auris.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PermissionsFragment : BaseFragment<PermissionContract.ViewModel>(), PermissionContract.View {

    override val viewModel: PermissionContract.ViewModel by viewModel()

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

        viewModel.view = this

        /*if (viewModel.checkPermissions(REQUIRED_PERMISSIONS)) {
            //TODO: This fragment should be used to show messages why certain permissions are needed and should contain a user entry point for requesting permissions again
        } else {
            viewModel.requestPermissions(REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }*/
    }

}