package com.example.musictheory.home.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musictheory.R
import com.example.musictheory.core.data.MainActivityCallback
import com.example.musictheory.databinding.FragmentHomeBodyBinding
import com.example.musictheory.home.presentation.ui.lists.adapters.CategoriesAdapter
import com.example.musictheory.home.presentation.ui.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeBodyFragment : Fragment() {
    lateinit var categoriesAdapter: CategoriesAdapter
//    lateinit var homeViewModel: HomeViewModel

    private val homeViewModel
            by hiltNavGraphViewModels<HomeViewModel>(R.id.nested_navigation_home)

    private var _binding: FragmentHomeBodyBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBodyBinding.inflate(inflater, container, false)
        val view = binding.root
        categoriesAdapter = CategoriesAdapter(
            object : CategoriesAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    val oid = categoriesAdapter.currentList.get(position).id.oid
                    if (activity is MainActivityCallback) {
                        (activity as MainActivityCallback).goTestFragment(oid)
                    }
                }
            },
            object : CategoriesAdapter.OnEditImgClickListener {
                override fun onItemClick(position: Int) {
                    val musicTest = categoriesAdapter.currentList.get(position)
                    if (activity is MainActivityCallback) {
                        (activity as MainActivityCallback).goAddTestFragment(musicTest.test_id)
                    }
                }
            }
        )
        setUpRecyclerView(categoriesAdapter)
        var token = ""
        if (activity is MainActivityCallback) {
            token = (activity as MainActivityCallback).getToken()
        }
        if (token != "")
            if (activity is MainActivityCallback) {
                val user = (activity as MainActivityCallback).getUser()
                Timber.i("t1 role home is ${user?.role} id is ${user?.userId}")
                when (user?.role) {
                    "admin" -> {
                        homeViewModel.getCategories(token, "")
                    }
                    "teacher" -> {
                        homeViewModel.getCategories(token, user?.userId ?: "")
                    }
                    else -> {
                        homeViewModel.getCategories(token, "1")
                    }
                }

            }

        else {
//            if(activity is MainActivityCallback){
//                (activity as MainActivityCallback).goAccount("","")
//            }
        }

        homeViewModel.categories.observe(
            viewLifecycleOwner,
            Observer { response ->
                binding.apply {
                    Timber.i("submit")
                    if (activity is MainActivityCallback) {
                        val user =  (activity as MainActivityCallback).getUser()
                        Timber.i("t1 roe ${user?.role}")
                        if (user?.role != null && (user.role=="admin" || user.role=="teacher")){
                            categoriesAdapter.setVisibleEdit(true)
                        } else categoriesAdapter.setVisibleEdit(false)
                    }
                    categoriesAdapter.submitList(response)


                }
            }
        )

        return view
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setUpRecyclerView(categoriesAdapter: CategoriesAdapter) {
        binding.testCategoryRecyclerView.apply {
            adapter = categoriesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}
