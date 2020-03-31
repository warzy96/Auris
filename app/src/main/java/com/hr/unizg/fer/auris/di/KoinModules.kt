package com.hr.unizg.fer.auris.di

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.viewModelScope
import com.example.textrecognition.TextAnalyzer
import com.example.textrecognition.TextRecognizer
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer
import com.hr.unizg.fer.auris.MainActivity
import com.hr.unizg.fer.auris.MainActivityViewModel
import com.hr.unizg.fer.auris.MainActivityViewModelImpl
import com.hr.unizg.fer.auris.camera.actions.ActionsFragmentViewModel
import com.hr.unizg.fer.auris.camera.actions.ActionsFragmentViewModelImpl
import com.hr.unizg.fer.auris.camera.viewfinder.ViewFinderViewModel
import com.hr.unizg.fer.auris.camera.viewfinder.ViewFinderViewModelImpl
import com.hr.unizg.fer.auris.navigation.FragmentRouterImpl
import com.hr.unizg.fer.auris.navigation.Router
import com.hr.unizg.fer.auris.permissions.PermissionViewModel
import com.hr.unizg.fer.auris.permissions.PermissionViewModelImpl
import com.hr.unizg.fer.auris.permissions.management.PermissionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val dataModule = module {

}

val fragmentModule = module {
    factory { (fragmentManager: FragmentManager) -> FragmentRouterImpl(fragmentManager) }
}

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@FlowPreview
fun activityModule(applicationContext: Context): Module {
    return module {
        single { PermissionHandler(applicationContext) }

        viewModel { MainActivityViewModelImpl(get()) as MainActivityViewModel }
        scope<MainActivity> {
            scoped { (fragmentManager: FragmentManager) -> Router(fragmentManager) }
        }

        viewModel { PermissionViewModelImpl(get()) as PermissionViewModel }
        viewModel { ViewFinderViewModelImpl() as ViewFinderViewModel }
        viewModel { ActionsFragmentViewModelImpl() as ActionsFragmentViewModel }

        scope<ViewFinderViewModelImpl> {
            factory { (viewModel: ViewFinderViewModelImpl) -> TextAnalyzer(viewModel.viewModelScope) }
            factory { (textAnalyzer: TextAnalyzer, textDetector: FirebaseVisionTextRecognizer) -> TextRecognizer(textAnalyzer, textDetector) }
        }
    }
}