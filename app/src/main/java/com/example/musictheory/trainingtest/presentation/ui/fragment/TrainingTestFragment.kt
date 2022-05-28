package com.example.musictheory.trainingtest.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.musictheory.R
import com.example.musictheory.core.data.MainActivityCallback
import com.example.musictheory.core.data.model.ServerResponse
import com.example.musictheory.databinding.TrainingTestFragmentBinding
import com.example.musictheory.trainingtest.presentation.ui.viewmodel.TrainingTestViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

@AndroidEntryPoint
class TrainingTestFragment : Fragment() {

    private val trainingTestViewModel: TrainingTestViewModel
    by hiltNavGraphViewModels<TrainingTestViewModel>(R.id.nested_navigation_training_test)
    private var _binding: TrainingTestFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TrainingTestFragmentBinding.inflate(inflater)

        initNestedFragments()


        // Вызов запроса к серверу через view model
        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    trainingTestViewModel.goNextEvent.collect {
//                        if (it) {
//                            var nextBodyFragment: Fragment? = null
//                            when (trainingTestViewModel.uiType.value) {
//                                "none" -> {
//                                    nextBodyFragment =
//                                        TrainingTestBodyWithStaveFragment()
//                                }
//                                "stave" -> {
//                                    nextBodyFragment =
//                                        TrainingTestBodyWithStaveFragment()
//                                }
//                            }
//                            if (nextBodyFragment != null) {
//                                childFragmentManager.commit {
//                                    disallowAddToBackStack()
//                                    replace(
//                                        R.id.bodyTrainingTest,
//                                        nextBodyFragment
//                                    )
//                                }
//                            }
//                        }
                    }
                }
                launch {
                    trainingTestViewModel.goResultEvent.collect {
                        if (it != 0L) {
                            if (activity is MainActivityCallback) {
                                (activity as MainActivityCallback).goResultFragment(it)
                            }
                        }
                    }
                }
                launch {
                    trainingTestViewModel.currentQuestionOid.collect {
                        lifecycleScope.launch {
                            try {
                                var token = ""
                                if (activity is MainActivityCallback) {
                                    token = (activity as MainActivityCallback).getToken()
                                }
                                if (token!="") {
                                    val tests = async {

                                        trainingTestViewModel.getTests(token)
                                    }
                                    trainingTestViewModel.getData(tests.await())
                                }
                            } catch (e: Exception){
                                Timber.v(e)
                            }

                        }
                    }
                }
                launch {
                    trainingTestViewModel.uiType.collect {
                        changeUiType()
                    }
                }
            }
        }
        val oid = arguments?.getString("categoryNumber")
        trainingTestViewModel.setOid(oid.toString())

        return binding.root
    }

    private fun changeUiType(){
            var nextBodyFragment: Fragment? = null
            when (trainingTestViewModel.uiType.value) {
                "none" -> {
                    nextBodyFragment =
                        TrainingTestBodyFragment()
                }
                "stave" -> {
                    nextBodyFragment =
                        TrainingTestBodyWithStaveFragment()
                }
                "stave random pick" -> {
                    nextBodyFragment =
                        TrainingTestBodyWithStaveFragment()
                }
                "picture" ->{
                    Timber.i("t1 picture fragment")
                    nextBodyFragment =
                        TrainingTestBodyWithImagePicture()
                }
                "audio" -> {

                }
            }
            if (nextBodyFragment != null) {
                childFragmentManager.commit {
                    disallowAddToBackStack()
                    replace(
                        R.id.bodyTrainingTest,
                        nextBodyFragment
                    )
                }
            }
    }

    private fun initNestedFragments() {
        val trainingTestHeaderFragment = TrainingTestHeaderFragment()
        childFragmentManager.beginTransaction().apply {
            add(R.id.headerTrainingTest, trainingTestHeaderFragment)
            commit()
        }

//        val trainingTestBodyFragment = TrainingTestBodyFragment()
//        childFragmentManager.beginTransaction().apply {
//            add(R.id.bodyTrainingTest, trainingTestBodyFragment)
//            commit()
//        }

//        val trainingTestBodyWithStaveFragment = TrainingTestBodyWithStaveFragment()
//        childFragmentManager.beginTransaction().apply {
//            add(R.id.bodyTrainingTest, trainingTestBodyWithStaveFragment)
//            commit()
//        }

        val trainingFragmentFooterFragment = TrainingTestFooterFragment()
        childFragmentManager.beginTransaction().apply {
            add(R.id.footerTrainingTest, trainingFragmentFooterFragment)
            commit()
        }

        if (activity is MainActivityCallback) {
            (activity as MainActivityCallback).hideBottomNavigationView()
        }
    }

    private suspend fun showDataFromServer(
        serverResponse: ServerResponse
    ) = withContext(Dispatchers.Main) {
        Toast.makeText(
            context,
            "${serverResponse.data.collection.get(0).questionArray[0]}",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        if (activity is MainActivityCallback) {
            (activity as MainActivityCallback).showBottomNavigationView()
        }
    }
}
