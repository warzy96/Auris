package com.hr.unizg.fer.auris.di

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.google.firebase.ml.vision.FirebaseVision
import com.hr.unizg.fer.auris.MainActivity
import com.hr.unizg.fer.auris.camera.analysis.TextRecognizer
import com.hr.unizg.fer.auris.camera.viewfinder.ViewFinderContract
import com.hr.unizg.fer.auris.camera.viewfinder.ViewFinderViewModel
import com.hr.unizg.fer.auris.navigation.FragmentRouterImpl
import com.hr.unizg.fer.auris.navigation.Router
import com.hr.unizg.fer.auris.permissions.PermissionContract
import com.hr.unizg.fer.auris.permissions.PermissionHandler
import com.hr.unizg.fer.auris.permissions.PermissionViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {

}

val fragmentModule = module {
    single { (fragmentManager: FragmentManager) -> FragmentRouterImpl(fragmentManager) }
}

@ExperimentalCoroutinesApi
@FlowPreview
val activityModule = module {
    scope<MainActivity> {
        scoped { (activity: AppCompatActivity) -> PermissionHandler(activity) }
        viewModel { PermissionViewModel(get()) as PermissionContract.ViewModel }
    }

    viewModel { ViewFinderViewModel(get()) as ViewFinderContract.ViewModel }
    single { (fragmentManager: FragmentManager) -> Router(fragmentManager) }
    factory { FirebaseVision.getInstance().onDeviceTextRecognizer }
    factory { TextRecognizer(get()) }
}