package com.hr.unizg.fer.auris.camera.viewfinder

import com.hr.unizg.fer.auris.camera.analysis.TextRecognizer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
class ViewFinderViewModel(private val textRecognizer: TextRecognizer) : ViewFinderContract.ViewModel() {

}