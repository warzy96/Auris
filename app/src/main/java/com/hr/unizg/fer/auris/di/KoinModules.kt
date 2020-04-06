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
import com.hr.unizg.fer.auris.camera.actions.ActionsFragment
import com.hr.unizg.fer.auris.camera.actions.ActionsFragmentViewModel
import com.hr.unizg.fer.auris.camera.actions.ActionsFragmentViewModelImpl
import com.hr.unizg.fer.auris.camera.capturechannel.CapturedImageChannel
import com.hr.unizg.fer.auris.camera.capturechannel.PreviewActionsChannel
import com.hr.unizg.fer.auris.camera.preview.PreviewViewModel
import com.hr.unizg.fer.auris.camera.preview.PreviewViewModelImpl
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

const val MAIN_ACTIVITY_SCOPE_ID = "MainActivityScope"
const val MAIN_ACTIVITY = "MainActivity"

const val ACTIONS_FRAGMENT_SCOPE_ID = "ActionsFragment"

const val VIEW_FINDER_VIEW_MODEL_SCOPE_ID = "MainActivityScope"

val dataModule = module {

}

@ExperimentalCoroutinesApi
@FlowPreview
@InternalCoroutinesApi
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
        single { CapturedImageChannel() }
        scope<MainActivity> {
            scoped { (fragmentManager: FragmentManager) -> Router(fragmentManager) }
        }
        scope<ActionsFragment> {
            scoped { PreviewActionsChannel() }
        }
        scope<ViewFinderViewModelImpl> {
            factory { (viewModel: ViewFinderViewModelImpl) -> TextAnalyzer(viewModel.viewModelScope) }
            factory { (textAnalyzer: TextAnalyzer, textDetector: FirebaseVisionTextRecognizer) -> TextRecognizer(textAnalyzer, textDetector) }
        }
        viewModel { PreviewViewModelImpl(get()) as PreviewViewModel }
        viewModel { PermissionViewModelImpl(get()) as PermissionViewModel }
        viewModel { ViewFinderViewModelImpl(get()) as ViewFinderViewModel }
        viewModel { ActionsFragmentViewModelImpl() as ActionsFragmentViewModel }
    }
}
