package com.hr.unizg.fer.auris.camera.viewfinder

import androidx.camera.core.ImageAnalysis
import com.example.textrecognition.TextAnalyzer
import com.example.textrecognition.TextRecognizer
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.core.parameter.parametersOf
import org.koin.ext.getOrCreateScope

@ExperimentalCoroutinesApi
@FlowPreview
class ViewFinderViewModel : ViewFinderContract.ViewModel() {
    private val textAnalyzer: TextAnalyzer by getOrCreateScope().inject { parametersOf(this) }
    private val textDetector: FirebaseVisionTextRecognizer by getOrCreateScope().inject()
    private val textRecognizer: TextRecognizer by getOrCreateScope().inject { parametersOf(textAnalyzer, textDetector) }

    override fun provideImageAnalyzer(): ImageAnalysis.Analyzer = textAnalyzer
}