package com.hr.unizg.fer.auris

import android.app.Application
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig
import com.google.firebase.FirebaseApp
import com.hr.unizg.fer.auris.di.activityModule
import com.hr.unizg.fer.auris.di.dataModule
import com.hr.unizg.fer.auris.di.fragmentModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@InternalCoroutinesApi
class AurisApp : Application(), CameraXConfig.Provider {
    override fun getCameraXConfig(): CameraXConfig {
        return Camera2Config.defaultConfig()
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        startKoin {
            androidContext(this@AurisApp)
            modules(
                dataModule,
                fragmentModule,
                activityModule(applicationContext)
            )
        }
    }
}