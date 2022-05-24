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
        val scale = 1
        val widthSpec = MeasureSpec.makeMeasureSpec((16*density*scale).toInt(), MeasureSpec.EXACTLY)
        val heightSpec = MeasureSpec.makeMeasureSpec((11*density*scale).toInt(), MeasureSpec.EXACTLY)
        for (i in 0 until childCount) {
            getChildAt(i).measure(widthSpec, heightSpec)

        }
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        var x = paddingLeft + 100
        var y = 0
        var xBiasNext = 0
        for (i in 0 until childCount) {
            getChildAt(i).let{
                if(it is IntNoteImage){
                    y = defineLineCoordinate(it.posVertical)
                    Timber.v("t1 ${it.posHorizontal}")
                    if (it.posHorizontal =="double_stops") {
                        xBiasNext -= measuredWidth / childCount
                        if(getChildAt(i+1)!=null && getChildAt(i+1) is IntNoteImage
                            && ((getChildAt(i+1) as IntNoteImage).posVertical) - it.posVertical  <= 0.5f){
                            xBiasNext += (13f*density).toInt()
                        }
                    } else xBiasNext = 0
                }
                it.layout(
                    x,
                    y,
                    x+it.measuredWidth,
                    y+it.measuredHeight
                )
                x += xBiasNext + measuredWidth / childCount
                y += (5f*density).toInt()
            }

        }

    }



    override fun dispatchDraw(canvas: Canvas?) {
        val path = Path().apply{
            val width = MeasureSpec.getSize(measuredWidth).toFloat()
            // лучше вынести вычисления отсюда
            for(i in 10..50 step 10) {
                moveTo(paddingStart.toFloat(), i * density)
                lineTo(width - paddingEnd.toFloat(), i * density)
            }
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

    private fun defineLineCoordinate(lineNum: Float): Int{
        var biasView = 0
        return ((11-lineNum*2)*5f*density).toInt()

    }

}