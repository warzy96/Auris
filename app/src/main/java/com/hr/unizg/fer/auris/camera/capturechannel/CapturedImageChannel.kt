package com.hr.unizg.fer.auris.camera.capturechannel

import android.graphics.Bitmap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow

@FlowPreview
@ExperimentalCoroutinesApi
class CapturedImageChannel {

    private val capturedImageChannel: ConflatedBroadcastChannel<Bitmap?> = ConflatedBroadcastChannel()

    fun getImageFlow() = capturedImageChannel.asFlow()

    suspend fun sendImage(imageBitmap: Bitmap?) {
        capturedImageChannel.send(imageBitmap)
    }
}
