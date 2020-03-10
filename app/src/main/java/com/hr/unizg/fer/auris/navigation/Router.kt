package com.hr.unizg.fer.auris.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.hr.unizg.fer.auris.R
import com.hr.unizg.fer.auris.camera.actions.ActionsFragment
import com.hr.unizg.fer.auris.permissions.PermissionsFragment

@IdRes
private const val ACTIVITY_CONTAINER_ID = R.id.fragment_container_view

class Router(private val fragmentManager: FragmentManager) {

    fun showPermissionsFragment() {
        fragmentManager.beginTransaction()
            .replace(ACTIVITY_CONTAINER_ID, PermissionsFragment.newInstance(), PermissionsFragment.TAG)
            .commit()
    }

    fun showCameraFragments() {
        fragmentManager.beginTransaction()
            .replace(ACTIVITY_CONTAINER_ID, ActionsFragment.newInstance(), ActionsFragment.TAG)
            .commit()
    }

}