package com.hr.unizg.fer.auris.camera.viewfinder

import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.lifecycle.viewModelScope
import com.example.textrecognition.TextAnalyzer
import com.example.textrecognition.TextRecognizer
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.text.FirebaseVisionText
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import org.koin.core.parameter.parametersOf
import org.koin.ext.getOrCreateScope

@ExperimentalCoroutinesApi
@FlowPreview
class ViewFinderViewModel : ViewFinderContract.ViewModel() {
    private val textAnalyzer: TextAnalyzer by getOrCreateScope().inject { parametersOf(this) }
    private val textDetector: FirebaseVisionTextRecognizer = FirebaseVision.getInstance().onDeviceTextRecognizer
    private val textRecognizer: TextRecognizer by getOrCreateScope().inject { parametersOf(textAnalyzer, textDetector) }

    override fun provideImageAnalyzer(): ImageAnalysis.Analyzer = textAnalyzer

    override fun startTextRecognition() {
        viewModelScope.launch {
            textRecognizer.startTextRecognition(
                OnSuccessListener<FirebaseVisionText> { Log.d("asdfg", "${it.text}") },
                OnFailureListener { exception -> Log.d("asdfg", "$exception") })
        }
    }

}