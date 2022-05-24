package com.example.musictheory.account.presenter.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musictheory.account.presenter.list.viewholder.AnswerViewHolder
import com.example.musictheory.account.presenter.list.viewholder.OnAnswerChange
import com.example.musictheory.account.presenter.list.viewholder.OnCheckRightChangeListener
import com.example.musictheory.databinding.ItemAnswerBinding

class AnswersAdapter(onCheckRightChangeListener: OnCheckRightChangeListener, onAnswerChange: OnAnswerChange): RecyclerView.Adapter<AnswerViewHolder>() {

    var data = mutableListOf<String>()
    var checkedList = mutableListOf<Boolean>()

    private val onCheckRightChangeListener : OnCheckRightChangeListener = onCheckRightChangeListener
    private val onAnswerChange: OnAnswerChange = onAnswerChange

    fun updateData(list: MutableList<String>, checked : Int) {
        data = list
        checkedList = mutableListOf<Boolean>()
        for (i in 0..data.size){
            if(i<checked)
            checkedList.add(true)
            else checkedList.add(false)
        }
//        data.add(Question(questionText = "Добавить"))
        notifyDataSetChanged()
    }
    fun updateOneRow(answer: String, position: Int){
        data[position]=answer
        notifyItemChanged(position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        val binding = ItemAnswerBinding.inflate(
            LayoutInflater.from(parent.context, ),
            parent,
            false
        )
        return AnswerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        holder.bind(data[position], checkedList[position], onCheckRightChangeListener, onAnswerChange)
    }

    override fun getItemCount(): Int {
       return data.size
    }

}