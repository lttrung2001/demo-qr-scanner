package com.scan.barcodescanner

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class QrScannerView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val borderPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = 4f
            color = ContextCompat.getColor(context, android.R.color.holo_red_dark)
        }
    }
    private val eraserPaint by lazy {
        Paint().apply {
            xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        }
    }
    private val blackPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = ContextCompat.getColor(context, android.R.color.black)
        }
    }
    var borderRect = RectF()
    val fullViewRect = RectF()
    val cameraRect = RectF()

    val lengthF = 40f
    val radius = 20f
    val cameraPadding = 40f
    val cx by lazy {
        measuredWidth / 2f
    }
    val cy by lazy {
        measuredHeight / 3f
    }
    val mLeft by lazy {
        cx - 300f
    }
    val mTop by lazy {
        cy - 300f
    }
    val mRight by lazy {
        cx + 300f
    }
    val mBottom by lazy {
        cy + 300f
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        cameraRect.set(mLeft + cameraPadding, mTop + cameraPadding, mRight - cameraPadding, mBottom - cameraPadding)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        fullViewRect.set(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat())
        canvas.drawRect(fullViewRect, blackPaint)

        cameraRect.set(mLeft + cameraPadding, mTop + cameraPadding, mRight - cameraPadding, mBottom - cameraPadding)
        canvas.drawRoundRect(cameraRect, radius, radius, eraserPaint)

        canvas.drawLine(mLeft + radius, mTop, mLeft + lengthF, mTop, borderPaint) // Top Left to right
        canvas.drawLine(mLeft,mTop + radius, mLeft, mTop + lengthF, borderPaint) // Top Left to bottom
        borderRect.set(mLeft,mTop, mLeft + (radius*2), mTop + (radius*2))
        canvas.drawArc(borderRect,180f, 90f, false, borderPaint);

        canvas.drawLine(mRight - radius,mTop, mRight - lengthF, mTop, borderPaint); // Top Right to left
        canvas.drawLine(mRight,mTop + radius, mRight, mTop + lengthF, borderPaint); // Top Right to Bottom
        borderRect.set(mRight - (radius*2),mTop, mRight, mTop + (radius*2));
        canvas.drawArc(borderRect,270f, 90f, false, borderPaint);

        canvas.drawLine(mLeft + radius,mBottom, mLeft + lengthF, mBottom, borderPaint); // Bottom Left to right
        canvas.drawLine(mLeft,mBottom - radius, mLeft, mBottom - lengthF, borderPaint); // Bottom Left to top
        borderRect.set(mLeft,mBottom - (radius*2), mLeft + (radius*2), mBottom);
        canvas.drawArc(borderRect,90f, 90f, false, borderPaint);

        canvas.drawLine(mRight - radius,mBottom, mRight - lengthF, mBottom, borderPaint); // Bottom right to left
        canvas.drawLine(mRight,mBottom - radius, mRight, mBottom - lengthF, borderPaint); // Bottom right to top
        borderRect.set(mRight - (radius*2),mBottom - (radius*2), mRight, mBottom);
        canvas.drawArc(borderRect,0f, 90f, false, borderPaint);

    }
}