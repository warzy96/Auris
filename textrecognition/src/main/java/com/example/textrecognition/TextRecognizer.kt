package com.example.textrecognition

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.ml.vision.text.FirebaseVisionText
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import org.koin.core.KoinComponent

@FlowPreview
@ExperimentalCoroutinesApi
class TextRecognizer(private val textAnalyzer: TextAnalyzer, private val textDetector: FirebaseVisionTextRecognizer) : KoinComponent {

    suspend fun startTextRecognition(onSuccessListener: OnSuccessListener<FirebaseVisionText>, onFailureListener: OnFailureListener) {
        textAnalyzer.getImageChannelFlow().collect {
            textDetector
                .processImage(it)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener)
        }
    }
}