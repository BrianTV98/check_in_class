package com.example.check_in.ui.check_in

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.FrameLayout

class CornectTextureView(context: Context?, attrs: AttributeSet) : FrameLayout(context!!, attrs) {
    private val mRadius: Float
    private val mPath = Path()
    private var mRect = RectF()
    override fun onDraw(canvas: Canvas) {
        val savedState = canvas.save()
        val w = width.toFloat()
        val h = height.toFloat()
        mPath.reset()
        mRect[0f, 0f, w] = h
        mPath.addRoundRect(mRect, mRadius, mRadius, Path.Direction.CCW)
        mPath.close()
        val debug = canvas.clipPath(mPath)
        super.onDraw(canvas)
        canvas.restoreToCount(savedState)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val radius = 20f
        // compute the mPath
        val centerX = w / 2f // calculating half width // for Circle FrameLayout
        val centerY = h / 2f // calculating half height // for Circle FrameLayout
        mPath.reset()
        //mPath.addCircle(centerX, centerY, Math.min(centerX, centerY), Path.Direction.CW);
        mRect = RectF(0f, 0f, this.width.toFloat(), this.height.toFloat())
        mPath.addRoundRect(mRect, radius, radius, Path.Direction.CW)
        mPath.close()
    }

    override fun dispatchDraw(canvas: Canvas) {
        val save = canvas.save()
        canvas.clipPath(mPath)
        super.dispatchDraw(canvas)
        canvas.restoreToCount(save)
    }

    init {
        mRadius = attrs.getAttributeFloatValue(null, "corner_radius", 0f)
    }
}
