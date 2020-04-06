package com.hr.unizg.fer.auris.camera.preview

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.hr.unizg.fer.auris.camera.capturechannel.CapturedImageChannel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
@FlowPreview
@InternalCoroutinesApi
class PreviewViewModelImpl(private val capturedImageChannel: CapturedImageChannel) : PreviewViewModel() {

    private lateinit var imageFlowJob: Job
    private var onImageCapturedListener: (Bitmap) -> Unit = {}

    override fun setOnImageCapturedListener(listener: (Bitmap) -> Unit) {
        this.onImageCapturedListener = listener
    }

    override fun subscribeToImageFlow() {
        if (::imageFlowJob.isInitialized) {
            imageFlowJob.cancel()
        }
        imageFlowJob = viewModelScope.launch {
            capturedImageChannel.getImageFlow().collect {
                it?.let {
                    onImageCapturedListener(it)
                } ?: Log.d("asdfg", "Bitmap is null")
            }
        }
    }

}
