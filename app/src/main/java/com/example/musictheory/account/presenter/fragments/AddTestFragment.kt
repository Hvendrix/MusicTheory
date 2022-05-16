package com.example.musictheory.account.presenter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import com.example.musictheory.R
import com.example.musictheory.account.presenter.viewmodels.PersonalAccountViewModel
import com.example.musictheory.core.data.MainActivityCallback
import com.example.musictheory.databinding.AddTestFragmentBinding
import com.example.musictheory.trainingtest.data.model.Question
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
@AndroidEntryPoint
class AddTestFragment : Fragment() {

    companion object {
        fun newInstance() = AddTestFragment()
    }

//    private lateinit var viewModel: AddTestViewModel
    private val personalAccountViewModel: PersonalAccountViewModel
    by hiltNavGraphViewModels<PersonalAccountViewModel>(R.id.nested_personal_account)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = AddTestFragmentBinding.inflate(inflater)

        binding.buttonAddTestAccount.setOnClickListener {
            lifecycleScope.launch {
                val str = binding.editTextIncorrectAnswersAccount.text.toString()
                var strArray = str.split(", ")
                var answerArray = ArrayList<ArrayList<String>>()
                var tmpArray = arrayListOf(binding.editTextAnswerAccount.text.toString())
                tmpArray.addAll(strArray)
                answerArray.add(tmpArray)

                var token = ""
                if (activity is MainActivityCallback) {
                    token = (activity as MainActivityCallback).getToken()
                }
                if(token!="") {
//                    personalAccountViewModel.postTestToServer(
//                        listOf(binding.editTextQuestionAccount.text.toString()),
//                        answerArray,
//                        listOf("none"),
//                        binding.editTextTestNameAccount.text.toString()
//                    )
                    personalAccountViewModel.postTestToServer(
                        token,
                        "определить ноту",
                        listOf("1"),
                        listOf(Question(
                            listOf("ми", "фа", "соль", "ля", "си", "до", "ре"),
                            "",
                            listOf(),
                            mapOf(
                                "count" to 1,
                                "notes" to "from_answers"
                            ),
                            "какая это нота",
                            "stave"
                        )),
                       "2"
                    )
                }
            }
        }

        binding.buttonDeleteTestAccount.setOnClickListener {
            lifecycleScope.launch {
                personalAccountViewModel.postDeleteTest()
            }
        }
        return binding.root
    }
}
