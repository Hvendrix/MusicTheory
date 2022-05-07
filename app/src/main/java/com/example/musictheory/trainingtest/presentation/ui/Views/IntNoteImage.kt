package com.example.musictheory.trainingtest.presentation.ui.Views

import android.content.Context
import android.util.AttributeSet
import com.example.musictheory.R

class IntNoteImage @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : androidx.appcompat.widget.AppCompatImageView(context, attrs) {
    var posVertical = 0f
    init {
        attrs?.let { initIntImage(it, 0) }
    }

    private fun initIntImage(attrs: AttributeSet, defStyleAttr : Int){
        val attrsFromContext = context.obtainStyledAttributes(attrs,R.styleable.IntNoteImage)
        posVertical = attrsFromContext.getFloat(R.styleable.IntNoteImage_numLineVertical, 0f)

    }
    fun setAttr(numLine: Float){
        posVertical = numLine
    }

    fun getAttr() : Float{
       return posVertical
    }

}