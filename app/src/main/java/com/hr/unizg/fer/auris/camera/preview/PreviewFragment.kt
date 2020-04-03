package com.hr.unizg.fer.auris.camera.preview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.api.load
import com.hr.unizg.fer.auris.R
import com.hr.unizg.fer.auris.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_preview.*
import org.koin.android.ext.android.inject

class PreviewFragment : BaseFragment<PreviewViewModel>() {

    companion object {
        const val TAG = "PreviewFragment"
        fun newInstance(): Fragment {
            return PreviewFragment()
        }
    }

    override val viewModel: PreviewViewModel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_preview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        photoView.load("https://images.unsplash.com/photo-1577643816920-65b43ba99fba?ixlib=rb-1.2.1&auto=format&fit=crop&w=3300&q=80") {
            crossfade(true)
        }
    }
}