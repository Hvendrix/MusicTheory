package com.example.musictheory.home.presentation.ui.lists.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.musictheory.databinding.TestCategoryCardBinding
import com.example.musictheory.home.presentation.ui.lists.adapters.CategoriesAdapter
import com.example.musictheory.trainingtest.data.model.MusicTest

class CategoriesViewHolder(
    private val binding: TestCategoryCardBinding,
    listener: CategoriesAdapter.OnItemClickListener,
    listenerEditImgClickListener: CategoriesAdapter.OnEditImgClickListener
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(data: MusicTest, b: Boolean) {
        with(binding) {
            categoryTitle.text = data.testName
        }
        if(b)
            binding.imgEdit.visibility = View.VISIBLE
        else
            binding.imgEdit.visibility = View.GONE
    }

    init {
        binding.categoryTitle.setOnClickListener {
            listener.onItemClick(adapterPosition)
        }
        binding.imgEdit.setOnClickListener {
            listenerEditImgClickListener.onItemClick(adapterPosition)
        }


//        itemView.setOnClickListener {
//            listener.onItemClick(adapterPosition)
//        }

    }
}
