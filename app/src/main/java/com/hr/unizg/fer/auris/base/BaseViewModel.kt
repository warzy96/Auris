package com.hr.unizg.fer.auris.base

abstract class BaseViewModel<V : BaseContract.View> : BaseContract.ViewModel() {

    lateinit var view: V
}