package com.hr.unizg.fer.auris.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.hr.unizg.fer.auris.R
import com.hr.unizg.fer.auris.camera.viewfinder.ViewFinderFragment

@IdRes
private const val ACTIONS_FRAGMENT_CONTAINER_ID = R.id.fragment_actions_container_view

class FragmentRouterImpl(private val fragmentManager: FragmentManager) {

    fun showViewFinderFragment() {
        fragmentManager.beginTransaction()
            .replace(ACTIONS_FRAGMENT_CONTAINER_ID, ViewFinderFragment.newInstance(), ViewFinderFragment.TAG)
            .commit()
    }
}