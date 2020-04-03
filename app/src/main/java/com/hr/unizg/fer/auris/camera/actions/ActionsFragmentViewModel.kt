package com.hr.unizg.fer.auris.camera.actions

import com.hr.unizg.fer.auris.MainActivity
import com.hr.unizg.fer.auris.base.BaseViewModel
import com.hr.unizg.fer.auris.di.MAIN_ACTIVITY_SCOPE_ID
import com.hr.unizg.fer.auris.navigation.Router
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.java.KoinJavaComponent.getKoin
import kotlin.reflect.KFunction1

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
abstract class ActionsFragmentViewModel : BaseViewModel() {

    protected val router: Router by getKoin().getOrCreateScope<MainActivity>(MAIN_ACTIVITY_SCOPE_ID).inject()

    abstract fun dispatch(action: KFunction1<Router, Unit>)
}
