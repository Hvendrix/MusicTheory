package com.example.musictheory.trainingtest.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.musictheory.R
import com.example.musictheory.core.data.MainActivityCallback
import com.example.musictheory.databinding.FragmentResultBinding
import com.example.musictheory.model.Result
import com.example.musictheory.trainingtest.data.model.MusicTestEntity
import com.example.musictheory.trainingtest.presentation.ui.viewmodel.ResultViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultFragment : Fragment() {

    private val viewModel: ResultViewModel by viewModels()
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.result.observe(
            viewLifecycleOwner,
            { result ->
//                Toast.makeText(context, result.mistakeArray?.get(0)?.get(0), Toast.LENGTH_SHORT).show()
                getTest(result)
            }
        )

        viewModel.test.observe(
            viewLifecycleOwner,
            { test ->
//                Toast.makeText(context, test.id, Toast.LENGTH_LONG).show()
            }
        )

        arguments?.let {
            val id = it.getLong(ARG_ID_RESULT)
            viewModel.getResultById(id)
        }

        if (activity is MainActivityCallback) {
            (activity as MainActivityCallback).hideBottomNavigationView()
        }
    }
//    private fun insertTestData() {
//        viewModel.saveTest(Test("1", 0, listOf("are you "), listOf("yes"), TypeQuestion.SIMPLE))
//        viewModel.saveResult(Result(1, "1", 1, listOf(Mistake(1, listOf("ошибка", "ошибка")))))
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getTest(result: Result) {
        viewModel.test.observe(
            viewLifecycleOwner,
            { test ->
                setData(result, test)
            }
        )
        viewModel.getTestById(result.idTest)
    }

    private fun setData(result: Result, test: MusicTestEntity) {
        val allCount = test.questionArray.size
        binding.tvCorrectCount.text = requireContext().getString(
            R.string.result_from,
            result.mistakeCount,
            allCount
        )
        val numStars = R.integer.num_stars
        binding.progress.progress = 5 * (allCount - result.mistakeCount) / allCount
    }

    companion object {

        private const val ARG_ID_RESULT = "id_result"

//        fun newInstance(idResult: Long): Fragment {
//            return ResultFragment().apply {
//                arguments = Bundle().apply {
//                    putLong(ARG_ID_RESULT, idResult)
//                }
//            }
//        }
    }
}
