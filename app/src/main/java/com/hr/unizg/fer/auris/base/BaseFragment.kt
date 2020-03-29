package com.hr.unizg.fer.auris.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

abstract class BaseFragment<VM : ViewModel> : Fragment() {

    abstract val viewModel: VM
}