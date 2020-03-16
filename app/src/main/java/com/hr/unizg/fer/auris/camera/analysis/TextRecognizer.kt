package com.hr.unizg.fer.auris.camera.analysis

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.ml.vision.text.FirebaseVisionText
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import org.koin.core.KoinComponent
import org.koin.core.inject

@FlowPreview
@ExperimentalCoroutinesApi
class TextRecognizer(private val textAnalyzer: TextAnalyzer) : KoinComponent {

    private val textDetector: FirebaseVisionTextRecognizer by inject()

    suspend fun startTextRecognition(onSuccessListener: OnSuccessListener<FirebaseVisionText>, onFailureListener: OnFailureListener) {
        //TODO: Replace this with adequate coruotine scope
        textAnalyzer.getImageChannelFlow().collect {
            textDetector
                .processImage(it)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener)
        }
    }
}