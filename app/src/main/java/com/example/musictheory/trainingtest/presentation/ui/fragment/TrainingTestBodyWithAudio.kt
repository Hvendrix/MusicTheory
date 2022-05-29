package com.example.musictheory.trainingtest.presentation.ui.fragment

import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
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
import com.example.musictheory.R
import com.example.musictheory.databinding.FragmentTrainingTestBodyWithAudioBinding
import com.example.musictheory.trainingtest.presentation.ui.list.adapter.AdapterTrainingTestBody
import com.example.musictheory.trainingtest.presentation.ui.list.viewholder.OnItemClickListener
import com.example.musictheory.trainingtest.presentation.ui.viewmodel.TrainingTestViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TrainingTestBodyWithAudio : Fragment(), OnItemClickListener {

    private var _binding: FragmentTrainingTestBodyWithAudioBinding? = null
    private val binding get() = _binding!!
    private val trainingTestViewModel: TrainingTestViewModel
            by hiltNavGraphViewModels<TrainingTestViewModel>(R.id.nested_navigation_training_test)

    var mPlayer: MediaPlayer? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTrainingTestBodyWithAudioBinding.inflate(inflater)

        val adapter = AdapterTrainingTestBody(this)

        binding.signList.adapter = adapter

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    trainingTestViewModel.currentAudio
                        .collect {
                            if(it!=null) {
                                if(it is Int && it!=0) {
                                    mPlayer =
                                        MediaPlayer.create(this@TrainingTestBodyWithAudio.requireContext(),
                                            trainingTestViewModel.currentAudio.value as Int)
                                }
                                if(it is String && !it.isNullOrBlank()) {
                                    mPlayer =
                                        MediaPlayer.create(this@TrainingTestBodyWithAudio.requireContext(),
                                            Uri.parse(trainingTestViewModel.currentAudio.value as String))
                                }
                                if(mPlayer!=null){
                                    mPlayer!!.setOnCompletionListener { stopPlay() }
                                    binding.playButton.setOnClickListener {
                                        play()
                                    }
                                    binding.stopButton.setOnClickListener {
                                        stop(it)
                                    }
//                                binding.pauseButton.setEnabled(false)
                                    binding.stopButton.setEnabled(false)
                                }

                            }
                        }
                }
                launch {
                    trainingTestViewModel.answersList
                        .collect {
                            adapter.updateData(it)
                        }
                }
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }



    private fun stopPlay() {
        mPlayer!!.stop()
//        binding.pauseButton.setEnabled(false)
        binding.stopButton.setEnabled(false)
        try {
            mPlayer!!.prepare()
            mPlayer!!.seekTo(0)
            binding.playButton.setEnabled(true)
        } catch (t: Throwable) {
//            Toast.makeText(this, t.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun play() {
        mPlayer!!.start()
        binding.playButton.setEnabled(false)
//        binding.pauseButton.setEnabled(true)
        binding.stopButton.setEnabled(true)
    }

    fun pause(view: View?) {
        mPlayer!!.pause()
        binding.playButton.setEnabled(true)
//        binding.pauseButton.setEnabled(false)
        binding.stopButton.setEnabled(true)
    }

    fun stop(view: View?) {
        stopPlay()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mPlayer!!.isPlaying) {
            stopPlay()
        }
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