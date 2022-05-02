package com.example.musictheory.home.presentation.ui.lists

import androidx.recyclerview.widget.DiffUtil
import com.example.musictheory.trainingtest.data.model.MusicTest

val differCallback = object : DiffUtil.ItemCallback<MusicTest>() {
    override fun areItemsTheSame(oldItem: MusicTest, newItem: MusicTest): Boolean {
        return oldItem.testName == newItem.testName
    }

    override fun areContentsTheSame(oldItem: MusicTest, newItem: MusicTest): Boolean {
        return oldItem == newItem
    }
}
