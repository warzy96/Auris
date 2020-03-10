package com.hr.unizg.fer.auris.base

import androidx.fragment.app.Fragment

abstract class BaseFragment<VM : BaseContract.ViewModel> : Fragment(), BaseContract.View {

    abstract val viewModel: VM
}