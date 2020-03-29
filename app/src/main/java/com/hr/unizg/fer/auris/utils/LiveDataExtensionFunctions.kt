package com.hr.unizg.fer.auris.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LifecycleOwner.observe(data: LiveData<T>, observer: (T) -> Unit) {
    data.observe(this, Observer(observer))
}