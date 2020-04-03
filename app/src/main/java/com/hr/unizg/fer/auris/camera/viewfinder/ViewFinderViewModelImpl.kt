package com.hr.unizg.fer.auris.camera.viewfinder

import android.util.Log
import android.util.Size
import androidx.camera.core.ImageAnalysis
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.textrecognition.TextAnalysisDimensionListener
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
class ViewFinderViewModelImpl : ViewFinderViewModel() {
    private val textAnalyzer: TextAnalyzer by getOrCreateScope().inject { parametersOf(this) }
    private val textDetector: FirebaseVisionTextRecognizer = FirebaseVision.getInstance().onDeviceTextRecognizer
    private val textRecognizer: TextRecognizer by getOrCreateScope().inject { parametersOf(textAnalyzer, textDetector) }

    private val recognizedTextLiveData: MutableLiveData<FirebaseVisionText> = MutableLiveData()
    private val textAnalysisDimensionLiveData: MutableLiveData<Size> = MutableLiveData()

    private val textAnalysisDimensionListener: TextAnalysisDimensionListener = object : TextAnalysisDimensionListener {
        override fun onDimensionChanged(width: Int, height: Int) {
            textAnalysisDimensionLiveData.postValue(Size(width, height))
        }
    }

    override fun provideImageAnalyzer(): ImageAnalysis.Analyzer = textAnalyzer

    override fun getViewFinderModelLiveData(): LiveData<FirebaseVisionText> = recognizedTextLiveData

    override fun getTextAnalysisDimensionLiveData(): LiveData<Size> = textAnalysisDimensionLiveData

    override fun startTextRecognition() {
        setTextAnalysisDimensionListener(textAnalysisDimensionListener)

        viewModelScope.launch {
            textRecognizer.startTextRecognition(
                OnSuccessListener<FirebaseVisionText> { it?.let { recognizedTextLiveData.postValue(it) } },
                OnFailureListener { exception -> Log.d("asdfg", "$exception") })
        }
    }

    private fun setTextAnalysisDimensionListener(textAnalysisDimensionListener: TextAnalysisDimensionListener) {
        textAnalyzer.setTextAnalysisDimensionListener(textAnalysisDimensionListener)
    }
}
