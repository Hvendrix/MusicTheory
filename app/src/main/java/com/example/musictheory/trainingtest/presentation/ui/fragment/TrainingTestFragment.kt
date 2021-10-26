package com.example.musictheory.trainingtest.presentation.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.musictheory.R
import com.example.musictheory.data.MainActivityCallback
import com.example.musictheory.databinding.TrainingTestFragmentBinding
import com.example.musictheory.trainingtest.presentation.ui.viewmodel.TrainingTestViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class TrainingTestFragment : Fragment() {


    private lateinit var trainingTestViewModel: TrainingTestViewModel

    private var _binding : TrainingTestFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TrainingTestFragmentBinding.inflate(inflater)
        trainingTestViewModel = ViewModelProvider(this).get(TrainingTestViewModel::class.java)

        trainingTestViewModel.messageHello
            .onEach {
                Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
            } .launchIn(lifecycleScope)


        val trainingTestHeaderFragment = TrainingTestHeaderFragment()
        childFragmentManager.beginTransaction().apply {
            add(R.id.headerTrainingTest, trainingTestHeaderFragment)
            commit()
        }

        val trainingTestBodyFragment = TrainingTestBodyFragment()
        childFragmentManager.beginTransaction().apply {
            add(R.id.bodyTrainingTest, trainingTestBodyFragment)
            commit()
        }

        val trainingFragmentFooterFragment = TrainingTestFooterFragment()
        childFragmentManager.beginTransaction().apply {
            add(R.id.footerTrainingTest, trainingFragmentFooterFragment)
            commit()
        }


        if (activity is MainActivityCallback) {
            (activity as MainActivityCallback).hideBottomNavigationView()
        }


//        val navController = Navigation.findNavController(binding.root)

        return binding.root
    }


    override fun onPause() {
        super.onPause()
        if (activity is MainActivityCallback) {
            (activity as MainActivityCallback).showBottomNavigationView()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}