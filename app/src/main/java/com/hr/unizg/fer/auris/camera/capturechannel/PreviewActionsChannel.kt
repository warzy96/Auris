package com.hr.unizg.fer.auris.camera.capturechannel

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlin.reflect.KFunction1

@FlowPreview
@ExperimentalCoroutinesApi
class PreviewActionsChannel {

    private val actionSourceChannel = BroadcastChannel<KFunction1<CameraActions, Unit>>(1)

    fun getActionsFlow() = actionSourceChannel.asFlow()

    suspend fun sendAction(cameraAction: KFunction1<CameraActions, Unit>) = actionSourceChannel.send(cameraAction)
}
