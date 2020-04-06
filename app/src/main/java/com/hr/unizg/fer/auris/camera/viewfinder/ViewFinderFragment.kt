package com.hr.unizg.fer.auris.camera.viewfinder

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.common.util.concurrent.ListenableFuture
import com.google.firebase.ml.vision.text.FirebaseVisionText
import com.hr.unizg.fer.auris.MainActivity
import com.hr.unizg.fer.auris.R
import com.hr.unizg.fer.auris.base.BaseFragment
import com.hr.unizg.fer.auris.camera.actions.ActionsFragment
import com.hr.unizg.fer.auris.camera.capturechannel.CameraActions
import com.hr.unizg.fer.auris.camera.capturechannel.PreviewActionsChannel
import com.hr.unizg.fer.auris.di.ACTIONS_FRAGMENT_SCOPE_ID
import com.hr.unizg.fer.auris.di.MAIN_ACTIVITY_SCOPE_ID
import com.hr.unizg.fer.auris.navigation.Router
import com.hr.unizg.fer.auris.utils.aspectRatio
import com.hr.unizg.fer.auris.utils.isPortraitMode
import com.hr.unizg.fer.auris.utils.observe
import kotlinx.android.synthetic.main.fragment_viewfinder.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.java.KoinJavaComponent
import java.lang.Runnable
import java.util.concurrent.Executors

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@FlowPreview
class ViewFinderFragment : BaseFragment<ViewFinderViewModel>(), CameraActions {

    override val viewModel: ViewFinderViewModel by viewModel()

    private val router: Router by KoinJavaComponent.getKoin().getOrCreateScope<MainActivity>(MAIN_ACTIVITY_SCOPE_ID).inject()

    private val previewActionsChannel: PreviewActionsChannel by getKoin()
        .getOrCreateScope<ActionsFragment>(ACTIONS_FRAGMENT_SCOPE_ID)
        .inject()

    companion object {
        const val TAG = "ViewFinderFragment"
        fun newInstance(): Fragment {
            return ViewFinderFragment()
        }
    }

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var camera: Camera
    private lateinit var imagePreview: Preview
    private lateinit var cameraSelector: CameraSelector
    private lateinit var imageAnalysis: ImageAnalysis
    private lateinit var imageCapture: ImageCapture

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
        subscribeToCameraActions()
        observe(viewModel.getViewFinderModelLiveData(), ::render)
        observe(viewModel.getTextAnalysisDimensionLiveData(), ::onTextAnalysisDimensionChanged)
    }

    private fun subscribeToCameraActions() {
        lifecycleScope.launch(Dispatchers.Main) {
            previewActionsChannel.getActionsFlow().collect {
                it(this@ViewFinderFragment)
            }
        }
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

        imageAnalysis = setUpImageAnalysis()

        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()

            imagePreview = buildImagePreviewUseCase()

            setPreviewViewSize()

            cameraSelector = buildCameraSelectorUseCase()

            imageCapture = buildCaptureImageUseCase()

            camera = cameraProvider.bindToLifecycle(viewLifecycleOwner, cameraSelector, imageAnalysis, imageCapture, imagePreview)
        }, ContextCompat.getMainExecutor(context))
    }

    private fun setPreviewViewSize() {
        previewView.post {
            val resolution = imagePreview.attachedSurfaceResolution
            resolution?.let {
                previewView.layoutParams.height = if (isPortraitMode(context)) it.width else it.height
                previewView.layoutParams.width = if (isPortraitMode(context)) it.height else it.width
            }
        }
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
        .build()
        .apply {
            setSurfaceProvider(previewView.createSurfaceProvider(null))
        }

    private fun buildCaptureImageUseCase(): ImageCapture =
        ImageCapture.Builder()
            .setTargetRotation(previewView.display.rotation)
            .setTargetAspectRatio(screenAspectRatio)
            .setIoExecutor(Executors.newSingleThreadExecutor())
            .setFlashMode(ImageCapture.FLASH_MODE_AUTO)
            .build()

    private fun buildCameraSelectorUseCase() = CameraSelector.Builder()
        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
        .build()

    override fun capture() {
        imageCapture.takePicture(Executors.newSingleThreadExecutor(), object : ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                image.image?.let {
                    viewModel.postImage(it.toBitmap())
                }
                router.showPreviewFragment()
                Log.d("asdfg", "onCaptureSuccess")
                super.onCaptureSuccess(image)
            }

            override fun onError(exception: ImageCaptureException) {
                Log.d("asdfg", "$exception")
            }
        })
    }

    fun Image.toBitmap(): Bitmap {
        val buffer = planes[0].buffer
        buffer.rewind()
        val bytes = ByteArray(buffer.capacity())
        buffer.get(bytes)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }
}
