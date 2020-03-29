package com.example.textrecognition

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.text.FirebaseVisionText
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.sample
import org.koin.core.KoinComponent

private const val SAMPLE_PERIOD = 100L

@FlowPreview
@ExperimentalCoroutinesApi
class TextRecognizer(private val textAnalyzer: TextAnalyzer, private val textDetector: FirebaseVisionTextRecognizer) : KoinComponent {

    private lateinit var textDetectorTask: Task<FirebaseVisionText>

    suspend fun startTextRecognition(onSuccessListener: OnSuccessListener<FirebaseVisionText>, onFailureListener: OnFailureListener) {
        textAnalyzer.getImageChannelFlow().sample(SAMPLE_PERIOD).collect {
            if (!::textDetectorTask.isInitialized || !textDetectorTask.isRunning()) {
                textDetectorTask = textDetector
                    .processImage(it)
                    .addOnSuccessListener(onSuccessListener)
                    .addOnFailureListener(onFailureListener)
            }
        }
    }

    private fun Task<FirebaseVisionText>.isRunning() = !isCanceled && !isComplete && !isSuccessful
}