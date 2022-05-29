package com.example.musictheory.account.presenter.list.viewholder

import android.view.View
import android.widget.CompoundButton
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.musictheory.databinding.ItemAnswerBinding

class AnswerViewHolder(
private val binding: ItemAnswerBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        answer: String,
        checked: Boolean,
        onCheckRightChangeListener: OnCheckRightChangeListener,
        onAnswerChange: OnAnswerChange
    ) {
        binding.button.setText(answer)

//        binding.button.setOnFocusChangeListener { view, b ->
//            onAnswerChange.onAnswerChange(adapterPosition, binding.button.text.toString())
//        }
        binding.button.addTextChangedListener {
            onAnswerChange.onAnswerChange(adapterPosition, binding.button.text.toString())
        }


        if (checked && !binding.checkBox.isChecked)
            binding.checkBox.toggle()

        if(!checked &&binding.checkBox.isChecked)
            binding.checkBox.toggle()

        binding.checkBox.setOnCheckedChangeListener { compoundButton, b ->
//            onCheckRightChangeListener.onCheckedChanged(compoundButton, b, adapterPosition)

//        binding.buttonQuestion.text = question.questionText
//        binding.buttonQuestion.setOnClickListener {
//            onItemClickListener.onItemClick(question, adapterPosition)
//        }
        }
    }
}
interface OnCheckRightChangeListener {
    fun onCheckedChanged(p0: CompoundButton?, p1: Boolean, position: Int)
}

interface OnAnswerChange{
    fun onAnswerChange(position: Int, answer: String)
}


