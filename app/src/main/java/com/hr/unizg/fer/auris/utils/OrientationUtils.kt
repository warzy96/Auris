package com.hr.unizg.fer.auris.utils

import android.content.Context
import android.content.res.Configuration

fun isPortraitMode(context: Context?): Boolean {
    context?.let {
        val orientation = it.resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return false
        }
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            return true
        }
        return false
    } ?: return false
}
