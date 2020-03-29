package com.hr.unizg.fer.auris.di

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.viewModelScope
import com.example.textrecognition.TextAnalyzer
import com.example.textrecognition.TextRecognizer
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer
import com.hr.unizg.fer.auris.MainActivity
import com.hr.unizg.fer.auris.camera.viewfinder.ViewFinderViewModel
import com.hr.unizg.fer.auris.camera.viewfinder.ViewFinderViewModelImpl
import com.hr.unizg.fer.auris.navigation.FragmentRouterImpl
import com.hr.unizg.fer.auris.navigation.Router
import com.hr.unizg.fer.auris.permissions.PermissionHandler
import com.hr.unizg.fer.auris.permissions.PermissionViewModel
import com.hr.unizg.fer.auris.permissions.PermissionViewModelImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {

}

val fragmentModule = module {
    factory { (fragmentManager: FragmentManager) -> FragmentRouterImpl(fragmentManager) }
}

@ExperimentalCoroutinesApi
@FlowPreview
val activityModule = module {
    scope<MainActivity> {
        scoped { (activity: AppCompatActivity) -> PermissionHandler(activity) }
        viewModel { PermissionViewModelImpl(get()) as PermissionViewModel }
        scoped { (fragmentManager: FragmentManager) -> Router(fragmentManager) }
    }
    viewModel { ViewFinderViewModelImpl() as ViewFinderViewModel }
    scope<ViewFinderViewModelImpl> {
        factory { (viewModel: ViewFinderViewModelImpl) -> TextAnalyzer(viewModel.viewModelScope) }
        factory { (textAnalyzer: TextAnalyzer, textDetector: FirebaseVisionTextRecognizer) -> TextRecognizer(textAnalyzer, textDetector) }
    }

}