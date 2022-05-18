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
import com.example.musictheory.R
import com.example.musictheory.databinding.FragmentTrainingTestBodyWithStaveBinding
import com.example.musictheory.trainingtest.presentation.ui.Views.IntNoteImage
import com.example.musictheory.trainingtest.presentation.ui.list.adapter.AdapterTrainingTestBody
import com.example.musictheory.trainingtest.presentation.ui.list.viewholder.OnItemClickListener
import com.example.musictheory.trainingtest.presentation.ui.viewmodel.TrainingTestViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [TrainingTestBodyWithStaveFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class TrainingTestBodyWithStaveFragment : Fragment(), OnItemClickListener {

    private val trainingTestViewModel: TrainingTestViewModel
    by hiltNavGraphViewModels<TrainingTestViewModel>(R.id.nested_navigation_training_test)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentTrainingTestBodyWithStaveBinding.inflate(inflater)
        val adapter = AdapterTrainingTestBody(this)

//        adapter.updateData(listOf("1", "2"))
        binding.signList.adapter = adapter

//        lifecycleScope.launchWhenResumed {
//            trainingTestViewModel.answersList
//                .collect {
//                    adapter.updateData(it)
//                }
//        }

        lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED){
                launch {
                    trainingTestViewModel.answersList
                        .collect {
                            adapter.updateData(it)
                        }
                }
                launch {
                    trainingTestViewModel.displayedElements.collect{
                        binding.signsOnStave.removeAllViews()
                        it.forEach { el ->
                            createSignView(binding, el.lineNumVertical, horizontalPosition = el.horizontalPosition)
                        }
                        binding.signsOnStave.requestLayout()
                    }
                }

            }
        }


        return binding.root
    }

    override fun onItemClick(item: String) {
        if (item == trainingTestViewModel.currentRightAnswer.value) {
            trainingTestViewModel.goNext()
//        trainingTestViewModel.saveTest(
//            Test(
//                trainingTestViewModel.serverResponseCollection.value.id.oid,
//                idCategory = 0,
//                questions = listOf("are you "),
//                answers = listOf("yes"),
//                typeOfQuestion = TypeQuestion.SIMPLE
//            )
//        )
//        lifecycleScope.launch {
//            val id = withContext(Dispatchers.IO) {
//                var mistakeCountNotNull = 0
//                if(trainingTestViewModel.currentMistakeList.value.isNotEmpty()){
//                    mistakeCountNotNull = -1
//                }
//                trainingTestViewModel.saveResult(
//                    Result(
//                        idTest = trainingTestViewModel.serverResponseCollection.value.id.oid,
//                        mistakeCount = trainingTestViewModel.currentMistakeList.value.size + mistakeCountNotNull,
// //                        mistakeArray = listOf(Mistake(1, listOf("ошибка", "ошибка")))
//                                mistakeArray = trainingTestViewModel.currentMistakeList.value
//                    )
//                )
//            }
//            trainingTestViewModel.goResult(id)

//            if (activity is MainActivityCallback) {
//                (activity as MainActivityCallback).goResultFragment(id)
//            }
//        }
        } else {
            trainingTestViewModel.setMistake(item)
            Toast.makeText(context, "Неправильно", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createSignView(
        binding: FragmentTrainingTestBodyWithStaveBinding,
        numLine: Float = 0f,
        selectedImg: Int = R.drawable.ic_int_note,
        horizontalPosition: String = ""
    ){
        if(numLine==0f){
            return
        }
        val signView = IntNoteImage(this.requireContext())
        signView.id = View.generateViewId()
        signView.setImageResource(selectedImg)
        signView.setAttr(numLine, horizontalPosition)
        binding.signsOnStave.addView(signView)

    }
}
