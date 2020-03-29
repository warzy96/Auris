package com.hr.unizg.fer.auris.camera.viewfinder

import android.util.Size
import androidx.camera.core.ImageAnalysis
import androidx.lifecycle.LiveData
import com.google.firebase.ml.vision.text.FirebaseVisionText
import com.hr.unizg.fer.auris.base.BaseViewModel

abstract class ViewFinderViewModel : BaseViewModel() {
    abstract fun provideImageAnalyzer(): ImageAnalysis.Analyzer

    abstract fun getViewFinderModelLiveData(): LiveData<FirebaseVisionText>

    abstract fun getTextAnalysisDimensionLiveData(): LiveData<Size>

    abstract fun startTextRecognition()
}
