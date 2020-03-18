package com.example.textrecognition

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class TextAnalyzer(private val coroutineScope: CoroutineScope) : ImageAnalysis.Analyzer {

    private val imageChannel: BroadcastChannel<FirebaseVisionImage> = BroadcastChannel(1)

    fun getImageChannelFlow() = imageChannel.asFlow()

    private fun degreesToFirebaseRotation(degrees: Int): Int {
        return when (degrees) {
            0 -> FirebaseVisionImageMetadata.ROTATION_0
            90 -> FirebaseVisionImageMetadata.ROTATION_90
            180 -> FirebaseVisionImageMetadata.ROTATION_180
            270 -> FirebaseVisionImageMetadata.ROTATION_270
            else -> throw IllegalArgumentException(
                "Rotation must be 0, 90, 180, or 270."
            )
        }
    }

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        imageProxy.image?.let {
            val rotationDegrees = imageProxy.imageInfo.rotationDegrees
            val firebaseVisionImage = FirebaseVisionImage.fromMediaImage(it, degreesToFirebaseRotation(rotationDegrees))
            coroutineScope.launch { imageChannel.send(firebaseVisionImage) }
        }
        imageProxy.close()
    }
}