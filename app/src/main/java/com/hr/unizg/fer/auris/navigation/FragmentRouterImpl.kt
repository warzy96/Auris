package com.hr.unizg.fer.auris.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.hr.unizg.fer.auris.R
import com.hr.unizg.fer.auris.camera.viewfinder.ViewFinderFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi

@IdRes
private const val ACTIONS_FRAGMENT_CONTAINER_ID = R.id.fragment_actions_container_view

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class FragmentRouterImpl(private val fragmentManager: FragmentManager) {

    fun showViewFinderFragment() {
        if (fragmentManager.fragments.size == 0) {
            fragmentManager.beginTransaction()
                .replace(ACTIONS_FRAGMENT_CONTAINER_ID, ViewFinderFragment.newInstance(), ViewFinderFragment.TAG)
                .commit()
        }
    }
}
