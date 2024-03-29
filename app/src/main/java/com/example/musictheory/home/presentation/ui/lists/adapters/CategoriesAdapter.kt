package com.example.musictheory.home.presentation.ui.lists.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.musictheory.databinding.TestCategoryCardBinding
import com.example.musictheory.home.presentation.ui.lists.differCallback
import com.example.musictheory.home.presentation.ui.lists.viewholders.CategoriesViewHolder
import com.example.musictheory.trainingtest.data.model.MusicTest

class CategoriesAdapter(private var listener: OnItemClickListener, private var listenerEditImg: OnEditImgClickListener) :
    ListAdapter<MusicTest, CategoriesViewHolder>(differCallback) {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    interface OnEditImgClickListener {
        fun onItemClick(position: Int)
    }

    var editVisible : Boolean = false

    fun setVisibleEdit(b: Boolean){
        editVisible=b
//        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(
            TestCategoryCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            listener,
            listenerEditImg
        )
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bind(getItem(position), editVisible)
    }
}
