package com.example.musictheory.account.presenter.list.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.musictheory.databinding.ItemQuestionBinding
import com.example.musictheory.trainingtest.data.model.Question
import com.example.musictheory.trainingtest.presentation.ui.list.viewholder.OnItemClickListener

class QuestionViewHolder(
    private val binding: ItemQuestionBinding) : RecyclerView.ViewHolder(binding.root){

    fun bind(question: Question, onItemClickListener: OnItemQuestionClickListener) {
        binding.buttonQuestion.text = question.questionText
        binding.buttonQuestion.setOnClickListener {
            onItemClickListener.onItemClick(question, adapterPosition)
        }
    }

//    fun bind(text: String, onItemClickListener: OnItemQuestionClickListener){
//        binding.buttonQuestion.text = "Добавить"
//        binding.buttonQuestion.setOnClickListener {
//            onItemClickListener.onItemClick(adapterPosition)
//        }
//    }
}


interface OnItemQuestionClickListener {
    fun onItemClick(item: Question, position: Int)
}