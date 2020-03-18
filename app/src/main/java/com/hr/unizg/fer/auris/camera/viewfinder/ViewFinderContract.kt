package com.hr.unizg.fer.auris.camera.viewfinder

import androidx.camera.core.ImageAnalysis
import com.hr.unizg.fer.auris.base.BaseContract
import com.hr.unizg.fer.auris.base.BaseViewModel

interface ViewFinderContract {

    interface View : BaseContract.View {
    }

    abstract class ViewModel : BaseViewModel<View>() {
        abstract fun provideImageAnalyzer(): ImageAnalysis.Analyzer

        abstract fun startTextRecognition()
    }
}