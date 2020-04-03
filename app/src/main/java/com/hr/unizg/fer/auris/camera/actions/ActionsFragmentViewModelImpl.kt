package com.hr.unizg.fer.auris.camera.actions

import com.hr.unizg.fer.auris.navigation.Router
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlin.reflect.KFunction1

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class ActionsFragmentViewModelImpl : ActionsFragmentViewModel() {
    override fun dispatch(action: KFunction1<Router, Unit>) {
        action(router)
    }
}
