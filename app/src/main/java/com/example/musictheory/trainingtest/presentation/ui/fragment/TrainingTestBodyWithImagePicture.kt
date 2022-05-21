package com.example.musictheory.trainingtest.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.musictheory.R
import com.example.musictheory.databinding.TrainingTestBodyWithImageBinding
import com.example.musictheory.trainingtest.presentation.ui.list.adapter.AdapterTrainingTestBody
import com.example.musictheory.trainingtest.presentation.ui.list.viewholder.OnItemClickListener
import com.example.musictheory.trainingtest.presentation.ui.viewmodel.TrainingTestViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class TrainingTestBodyWithImagePicture: Fragment(), OnItemClickListener {


    private val trainingTestViewModel: TrainingTestViewModel
            by hiltNavGraphViewModels<TrainingTestViewModel>(R.id.nested_navigation_training_test)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = TrainingTestBodyWithImageBinding.inflate(inflater)
        val adapter = AdapterTrainingTestBody(this)

//        adapter.updateData(listOf("1", "2"))
        binding.signList.adapter = adapter


        lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED){
                launch {
                    trainingTestViewModel.answersList
                        .collect {
                            Timber.i("t1 answer list ${it}")
                            adapter.updateData(it)
                        }
                }
                launch {
                    trainingTestViewModel.imageAttachmentUrl.collect {
                        if(!it.isNullOrBlank()){
                            Glide.with(requireContext())
                                .load(it)
                                .into(binding.imageViewTrainigTest);
                        }
                    }
                }

            }
        }


        return binding.root
    }

    override fun onItemClick(item: String) {
        if (item == trainingTestViewModel.currentRightAnswer.value) {
            trainingTestViewModel.goNext()
        } else {
            trainingTestViewModel.setMistake(item)
            Toast.makeText(context, "Неправильно", Toast.LENGTH_SHORT).show()
        }
    }


}