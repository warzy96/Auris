package com.hr.unizg.fer.auris.camera.preview

import android.graphics.Bitmap
import com.hr.unizg.fer.auris.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
abstract class PreviewViewModel : BaseViewModel() {

    abstract fun subscribeToImageFlow()

    abstract fun setOnImageCapturedListener(listener: (Bitmap) -> Unit)
}
