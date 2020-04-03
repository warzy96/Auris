package com.hr.unizg.fer.auris.customcomponents

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.toRectF
import com.google.firebase.ml.vision.text.FirebaseVisionText

private const val DEFAULT_BORDER_COLOR = Color.WHITE
private const val DEFAULT_STROKE_WIDTH = 5f

class CameraPreviewOverlay : View {

    private lateinit var rectPaint: Paint

    private var borderColor: Int = DEFAULT_BORDER_COLOR
    private var borderStrokeWidth: Float = DEFAULT_STROKE_WIDTH
    private val boundingBoxes: ArrayList<Rect> = arrayListOf()

    private var previewWidth = 1
    private var previewHeight = 1

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    private fun init() {
        rectPaint = Paint().apply {
            strokeWidth = borderStrokeWidth
            color = borderColor
            strokeCap = Paint.Cap.ROUND
            style = Paint.Style.STROKE
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        boundingBoxes.forEach { canvas.drawRect(it.toRectF(), rectPaint) }
    }

    fun setTextData(firebaseVisionText: FirebaseVisionText) {
        var index = 0
        firebaseVisionText.textBlocks.forEach { textBlock ->
            textBlock.lines.forEach { line ->
                line.elements.forEach { element ->
                    element?.let {
                        it.boundingBox?.let { rect ->
                            val box = getBoundingBox(index, rect)
                            if (boundingBoxes.size <= index) {
                                boundingBoxes.add(box)
                            }
                            index++
                        }
                    }
                }
            }
        }
        boundingBoxes.removeAll { boundingBoxes.indexOf(it) >= index }
        invalidate()
    }

    private fun getBoundingBox(index: Int, rect: Rect): Rect =
        (if (boundingBoxes.size > index)
            boundingBoxes[index]
        else rect)
            .apply {
                left = translateX(rect.left)
                right = translateX(rect.right)
                top = translateY(rect.top)
                bottom = translateY(rect.bottom)
            }

    private fun translateX(value: Int): Int {
        return (value * width / previewWidth)
    }

    private fun translateY(value: Int): Int {
        return (value * height / previewHeight)
    }

    fun setScale(width: Int, height: Int) {
        previewWidth = width
        previewHeight = height
    }
}
