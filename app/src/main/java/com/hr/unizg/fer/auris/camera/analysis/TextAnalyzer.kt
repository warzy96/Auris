package com.hr.unizg.fer.auris.camera.analysis

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class TextAnalyzer : ImageAnalysis.Analyzer {

    private val imageChannel: BroadcastChannel<FirebaseVisionImage> = BroadcastChannel(1)

    fun getImageChannelFlow() = imageChannel.asFlow()

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        imageProxy.image?.let {
            val firebaseVisionImage = FirebaseVisionImage.fromMediaImage(it, imageProxy.imageInfo.rotationDegrees)
            //TODO: Replace this with adequate coruotine scope
            GlobalScope.launch { imageChannel.send(firebaseVisionImage) }
        }
        imageProxy.close()
    }
}