package com.example.musictheory.trainingtest.presentation.ui.Views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.annotation.AttrRes
import com.example.musictheory.R
import timber.log.Timber

class StaveViewGroup @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet,
    @AttrRes defStyleAttr: Int = 0,
) : ViewGroup(context, attributeSet, defStyleAttr){

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

//        val width = MeasureSpec.getSize(widthMeasureSpec)
//        val height = MeasureSpec.getSize(heightMeasureSpec)
//        if(childCount==null || childCount == 0){
//            setMeasuredDimension(width, height)
//            return
//        }
        Timber.i("t2 width size " + width + "height Size " + height)
//        val widthSpec = MeasureSpec.makeMeasureSpec(width / childCount, MeasureSpec.EXACTLY)
//        val heightSpec = MeasureSpec.makeMeasureSpec(height / childCount, MeasureSpec.EXACTLY)
        val specSize = (MeasureSpec.getSize(heightMeasureSpec))/10
        val widthSpec = MeasureSpec.makeMeasureSpec(specSize, MeasureSpec.EXACTLY)
        val heightSpec = MeasureSpec.makeMeasureSpec(specSize, MeasureSpec.EXACTLY)
//        val widthSpec = MeasureSpec.makeMeasureSpec(52, MeasureSpec.AT_MOST)
//        val heightSpec = MeasureSpec.makeMeasureSpec(52, MeasureSpec.AT_MOST)
        for (i in 0 until childCount) {
//            getChildAt(i).measure(widthSpec, heightSpec)
            getChildAt(i).measure(widthSpec, heightSpec)
//            getChildAt(i).measure(50, 50)

            Timber.i("t2 child width" + getChildAt(i).width + "height Size " + height)
        }

//        setMeasuredDimension(width, height)
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        var currentLeft = 0
        for (i in 0 until childCount) {
            getChildAt(i).layout(10*density.toInt(), 45*density.toInt(), (45*density.toInt()) + getChildAt(i).measuredWidth, getChildAt(i).measuredHeight )
//            getChildAt(i).layout(currentLeft, 0, currentLeft + measuredWidth / childCount, measuredHeight)
//            getChildAt(i).layout(currentLeft, 0, currentLeft + getChildAt(i).measuredWidth, getChildAt(i).measuredHeight)
//            getChildAt(i).measure(52, 52)
            currentLeft += measuredWidth / childCount
        }
    }


    override fun dispatchDraw(canvas: Canvas?) {

        val path = Path().apply{
            val width = (MeasureSpec.getSize(measuredWidth).toFloat()-10f)*density
            for(i in 10..50 step 10) {
                moveTo(10f * density, i * density)
                lineTo(width, i * density)
            }


//            moveTo(10f, 100f)
//            lineTo(300f, 100f)
//            moveTo(10f, 200f)
//            lineTo(300f, 200f)
//            moveTo(10f, 300f)
//            lineTo(300f, 300f)
//            moveTo(10f, 400f)
//            lineTo(300f, 400f)
//            lineTo(300f, 300f)

            close()
        }
        canvas?.drawPath(path, paint)
        super.dispatchDraw(canvas)
    }

    private val paint = Paint().apply{
        isAntiAlias = true
        color = resources.getColor(R.color.purple_900)
        strokeWidth = 3f
        style = Paint.Style.STROKE


    }
    val density = context.resources.displayMetrics.density

}