package com.example.musictheory.trainingtest.presentation.ui.Views

import android.content.Context
import android.util.AttributeSet
import com.example.musictheory.R
import timber.log.Timber

class IntNoteImage @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : androidx.appcompat.widget.AppCompatImageView(context, attrs) {
    var posVertical = 0f
    var posHorizontal = ""
    init {
        attrs?.let { initIntImage(it, 0) }
    }

    private fun initIntImage(attrs: AttributeSet, defStyleAttr : Int){
        val attrsFromContext = context.obtainStyledAttributes(attrs,R.styleable.IntNoteImage)
        posVertical = attrsFromContext.getFloat(R.styleable.IntNoteImage_numLineVertical, 0f)
        posHorizontal = attrsFromContext.getString(R.styleable.IntNoteImage_horizontal_position) ?: ""
        Timber.i("t1 position hor $posHorizontal")

    }
    fun setAttr(numLine: Float, horizontalPosition: String){
        posVertical = numLine
        posHorizontal = horizontalPosition
    }

//    fun getAttr() : Float{
//       return posVertical
//    }

//    fun getPosHorizontal(): String{
//        return posHorizontal
//    }



}