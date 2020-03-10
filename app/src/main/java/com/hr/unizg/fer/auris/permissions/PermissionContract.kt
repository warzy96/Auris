package com.hr.unizg.fer.auris.permissions

import com.hr.unizg.fer.auris.base.BaseContract
import com.hr.unizg.fer.auris.base.BaseViewModel

interface PermissionContract {

    interface View : BaseContract.View {

    }

    abstract class ViewModel : BaseViewModel<View>() {
    }
}