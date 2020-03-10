package com.hr.unizg.fer.auris.di

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.hr.unizg.fer.auris.MainActivity
import com.hr.unizg.fer.auris.camera.viewfinder.ViewFinderContract
import com.hr.unizg.fer.auris.camera.viewfinder.ViewFinderViewModel
import com.hr.unizg.fer.auris.navigation.FragmentRouterImpl
import com.hr.unizg.fer.auris.navigation.Router
import com.hr.unizg.fer.auris.permissions.PermissionContract
import com.hr.unizg.fer.auris.permissions.PermissionHandler
import com.hr.unizg.fer.auris.permissions.PermissionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {

}

val fragmentModule = module {
    single { (fragmentManager: FragmentManager) -> FragmentRouterImpl(fragmentManager) }
}

val activityModule = module {
    scope<MainActivity> {
        scoped { (activity: AppCompatActivity) -> PermissionHandler(activity) }
        viewModel { PermissionViewModel(get()) as PermissionContract.ViewModel }
    }

    viewModel { ViewFinderViewModel() as ViewFinderContract.ViewModel }
    single { (fragmentManager: FragmentManager) -> Router(fragmentManager) }
}