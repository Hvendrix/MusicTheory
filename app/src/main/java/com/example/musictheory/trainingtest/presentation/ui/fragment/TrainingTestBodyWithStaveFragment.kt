package com.example.musictheory.trainingtest.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import com.example.musictheory.R
import com.example.musictheory.databinding.FragmentTrainingTestBodyWithStaveBinding
import com.example.musictheory.trainingtest.presentation.ui.list.adapter.AdapterTrainingTestBody
import com.example.musictheory.trainingtest.presentation.ui.list.viewholder.OnItemClickListener
import com.example.musictheory.trainingtest.presentation.ui.viewmodel.TrainingTestViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

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

        lifecycleScope.launchWhenResumed {
            trainingTestViewModel.answersList
                .collect {
                    adapter.updateData(it)
                }
        }

        createSignView(binding)
        createSignView(binding)
        createSignView(binding)

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
        choicedImg: Int = R.drawable.int_note,
//        noteList: MutableList<ImageView>,
//        signTriple: Triple<Float, Float, String>,
        heightSize: Int = 52,
        widthSize: Int = 52,
//        horPos: Float,
//        vertPos: Float,
//        vertBias: Float,
//        horChang: Float
    ){
        //почему то срабаоывает на одиночный numPick

        val signView = ImageView(this.context)
        signView.id = View.generateViewId()
//        noteList.add(signView)
        signView.setImageResource(choicedImg)

        binding.signsOnStave.addView(signView)

//        binding.linParent.addView(signView)
//        binding.constraintLayout.addView(signView)
        signView.layoutParams.height = heightSize
        signView.layoutParams.width = widthSize
        binding.signsOnStave.requestLayout()
//        var set = ConstraintSet()
//        set.clone(binding.constraintLayout)
//        set.connect(signView.id, ConstraintSet.LEFT, binding.imgStave.id, ConstraintSet.LEFT)
//        set.connect(signView.id, ConstraintSet.RIGHT, binding.imgStave.id, ConstraintSet.RIGHT)
//        set.connect(signView.id, ConstraintSet.TOP, binding.imgStave.id, ConstraintSet.TOP)
//        set.connect(signView.id, ConstraintSet.BOTTOM, binding.imgStave.id, ConstraintSet.BOTTOM)
//        set.setHorizontalBias(signView.id, horPos + horChang)
//        set.setVerticalBias(signView.id, vertPos + vertBias)
//        set.applyTo(binding.constraintLayout)
//        Log.i("xxx", "Параметры были ${horPos}, $vertPos, $choicedImg, ")
    }
}
