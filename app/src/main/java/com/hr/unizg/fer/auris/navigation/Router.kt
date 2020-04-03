package com.hr.unizg.fer.auris.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.hr.unizg.fer.auris.R
import com.hr.unizg.fer.auris.camera.actions.ActionsFragment
import com.hr.unizg.fer.auris.camera.preview.PreviewFragment
import com.hr.unizg.fer.auris.permissions.PermissionsFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@IdRes
private const val ACTIVITY_CONTAINER_ID = R.id.fragment_container_view

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class Router(private val fragmentManager: FragmentManager) {

    fun showPermissionsFragment() {
        if (fragmentManager.findFragmentByTag(PermissionsFragment.TAG) == null) {
            fragmentManager.beginTransaction()
                .replace(ACTIVITY_CONTAINER_ID, PermissionsFragment.newInstance(), PermissionsFragment.TAG)
                .commit()
        }
    }

    fun showCameraFragments() {
        if (fragmentManager.findFragmentByTag(ActionsFragment.TAG) == null) {
            fragmentManager.beginTransaction()
                .replace(ACTIVITY_CONTAINER_ID, ActionsFragment.newInstance(), ActionsFragment.TAG)
                .commit()
        }
    }

    fun showPreviewFragment() {
        if (fragmentManager.findFragmentByTag(PreviewFragment.TAG) == null) {
            fragmentManager.beginTransaction()
                .replace(ACTIVITY_CONTAINER_ID, PreviewFragment.newInstance(), PreviewFragment.TAG)
                .addToBackStack(null)
                .commit()
        }
    }
}
