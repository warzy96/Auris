package com.hr.unizg.fer.auris.camera.viewfinder

import android.os.Bundle
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.common.util.concurrent.ListenableFuture
import com.hr.unizg.fer.auris.R
import com.hr.unizg.fer.auris.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_viewfinder.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.Executors

class ViewFinderFragment : BaseFragment<ViewFinderContract.ViewModel>(), ViewFinderContract.View {

    override val viewModel: ViewFinderContract.ViewModel by viewModel()

    companion object {
        const val TAG = "ViewFinderFragment"
        fun newInstance(): Fragment {
            return ViewFinderFragment()
        }
    }

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var imagePreview: Preview

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_viewfinder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.view = this

        context?.let {
            cameraProviderFuture = ProcessCameraProvider.getInstance(it)
        }

        previewView.post {
            startCamera()
            viewModel.startTextRecognition()
        }
    }

    private fun startCamera() {
        imagePreview = Preview.Builder().apply {
            setTargetAspectRatio(AspectRatio.RATIO_16_9)
            setTargetRotation(previewView.display.rotation)
        }.build()
        imagePreview.setSurfaceProvider(previewView.previewSurfaceProvider)

        val imageAnalysis = ImageAnalysis.Builder()
            .setTargetResolution(Size(480, 360))
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        imageAnalysis.setAnalyzer(Executors.newSingleThreadExecutor(), viewModel.provideImageAnalyzer())

        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()
            cameraProvider.bindToLifecycle(viewLifecycleOwner, cameraSelector, imageAnalysis, imagePreview)
        }, ContextCompat.getMainExecutor(context))
    }

}