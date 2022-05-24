package com.example.musictheory.account.presenter.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musictheory.account.presenter.list.viewholder.OnItemQuestionClickListener
import com.example.musictheory.account.presenter.list.viewholder.QuestionViewHolder
import com.example.musictheory.databinding.ItemQuestionBinding
import com.example.musictheory.trainingtest.data.model.Question
import com.example.musictheory.trainingtest.presentation.ui.list.viewholder.OnItemClickListener

class QuestionsAdapter( onItemClickListener: OnItemQuestionClickListener): RecyclerView.Adapter<QuestionViewHolder>(){

    var data = mutableListOf<Question>()

    private var mOnItemClickListener: OnItemQuestionClickListener = onItemClickListener

    fun updateData(list: MutableList<Question>) {
        data = list
//        data.add(Question(questionText = "Добавить"))
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val binding = ItemQuestionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return QuestionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.bind(data[position], mOnItemClickListener)
    }

    override fun getItemCount(): Int {
       return data.size
    }
}