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

    /**
     * Use this listener to scale recognized block of text when drawing them on the UI
     */
    private var textAnalysisDimensionListener: TextAnalysisDimensionListener = object : TextAnalysisDimensionListener {
        override fun onDimensionChanged(width: Int, height: Int) {
        }
    }

    fun setTextAnalysisDimensionListener(textAnalysisDimensionListener: TextAnalysisDimensionListener) {
        this.textAnalysisDimensionListener = textAnalysisDimensionListener
    }

    private var imageWidth = 0

    private var imageHeight = 0

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
            if (imageWidth != it.width || imageHeight != it.height) {
                imageWidth = it.width
                imageHeight = it.height
                textAnalysisDimensionListener.onDimensionChanged(it.width, it.height)
            }

            val rotationDegrees = imageProxy.imageInfo.rotationDegrees
            val firebaseVisionImage = FirebaseVisionImage.fromMediaImage(it, degreesToFirebaseRotation(rotationDegrees))
            coroutineScope.launch { imageChannel.send(firebaseVisionImage) }
        }
        imageProxy.close()
    }
}