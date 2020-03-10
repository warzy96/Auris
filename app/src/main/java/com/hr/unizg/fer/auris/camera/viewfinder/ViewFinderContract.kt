package com.hr.unizg.fer.auris.camera.viewfinder

import com.hr.unizg.fer.auris.base.BaseContract
import com.hr.unizg.fer.auris.base.BaseViewModel

interface ViewFinderContract {

    interface View : BaseContract.View {
    }

    abstract class ViewModel : BaseViewModel<View>() {
    }
}