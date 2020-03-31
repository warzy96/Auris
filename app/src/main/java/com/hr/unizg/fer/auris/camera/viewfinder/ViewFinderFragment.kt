package com.hr.unizg.fer.auris.camera.viewfinder

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.common.util.concurrent.ListenableFuture
import com.google.firebase.ml.vision.text.FirebaseVisionText
import com.hr.unizg.fer.auris.R
import com.hr.unizg.fer.auris.base.BaseFragment
import com.hr.unizg.fer.auris.utils.aspectRatio
import com.hr.unizg.fer.auris.utils.isPortraitMode
import com.hr.unizg.fer.auris.utils.observe
import kotlinx.android.synthetic.main.fragment_viewfinder.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.Executors

class ViewFinderFragment : BaseFragment<ViewFinderViewModel>() {

    override val viewModel: ViewFinderViewModel by viewModel()

    companion object {
        const val TAG = "ViewFinderFragment"
        fun newInstance(): Fragment {
            return ViewFinderFragment()
        }
    }

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var camera: Camera
    private lateinit var imagePreview: Preview
    private var screenAspectRatio: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_viewfinder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {
            cameraProviderFuture = ProcessCameraProvider.getInstance(it)
        }

        previewView.post {
            startCamera()
            viewModel.startTextRecognition()
        }
    }

    override fun onResume() {
        super.onResume()
        observe(viewModel.getViewFinderModelLiveData(), ::render)
        observe(viewModel.getTextAnalysisDimensionLiveData(), ::onTextAnalysisDimensionChanged)
    }

    private fun render(firebaseVisionText: FirebaseVisionText) {
        cameraPreviewOverlay.setTextData(firebaseVisionText)
    }

    private fun onTextAnalysisDimensionChanged(size: Size) {
        with(size) {
            if (isPortraitMode(context)) {
                cameraPreviewOverlay.post { cameraPreviewOverlay.setScale(height, width) }
            } else {
                cameraPreviewOverlay.post { cameraPreviewOverlay.setScale(width, height) }
            }
        }
    }

    private fun startCamera() {
        setAspectRatio()

        val imageAnalysis = setUpImageAnalysis()

        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()

            imagePreview = buildImagePreviewUseCase()

            val cameraSelector = buildCameraSelectorUseCase()

            camera = cameraProvider.bindToLifecycle(viewLifecycleOwner, cameraSelector, imageAnalysis, imagePreview)
        }, ContextCompat.getMainExecutor(context))
    }

    private fun setAspectRatio() {
        val metrics = DisplayMetrics().also { previewView.display.getRealMetrics(it) }

        screenAspectRatio = aspectRatio(metrics.widthPixels, metrics.heightPixels)
    }

    private fun setUpImageAnalysis(): ImageAnalysis =
        buildImageAnalysisUseCase().apply {
            setAnalyzer(Executors.newSingleThreadExecutor(), viewModel.provideImageAnalyzer())
        }

    private fun buildImageAnalysisUseCase(): ImageAnalysis =
        ImageAnalysis.Builder()
            .setTargetAspectRatio(screenAspectRatio)
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

    private fun buildImagePreviewUseCase(): Preview = Preview.Builder()
        .setTargetRotation(previewView.display.rotation)
        .setTargetResolution(Size(previewView.width, previewView.height))
        .build().apply {
            setSurfaceProvider(previewView.previewSurfaceProvider)
        }

    private fun buildCameraSelectorUseCase() = CameraSelector.Builder()
        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
        .build()
}
