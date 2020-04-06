package com.hr.unizg.fer.auris.camera.preview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.api.load
import com.hr.unizg.fer.auris.R
import com.hr.unizg.fer.auris.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_preview.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class PreviewFragment : BaseFragment<PreviewViewModel>() {

    companion object {
        const val TAG = "PreviewFragment"
        fun newInstance(): Fragment {
            return PreviewFragment()
        }
    }

    override val viewModel: PreviewViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_preview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setOnImageCapturedListener {
            Log.d("asdfg", "$viewModel")

            photoView.load(it) {
                crossfade(true)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.subscribeToImageFlow()
    }

    override fun onDestroy() {
        viewModel.setOnImageCapturedListener {  }
        super.onDestroy()
    }
}
