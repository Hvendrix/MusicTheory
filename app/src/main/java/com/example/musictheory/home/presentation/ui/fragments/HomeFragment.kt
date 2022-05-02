package com.example.musictheory.home.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Observer
import com.example.musictheory.R
import com.example.musictheory.home.presentation.ui.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

//    private lateinit var homeViewModel: HomeViewModel
    private val homeViewModel by hiltNavGraphViewModels<HomeViewModel>(R.id.nested_navigation_home)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
//         val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.categories.observe(
            viewLifecycleOwner,
            Observer {
                // textView.text = it
            }
        )

        return root
    }
}
