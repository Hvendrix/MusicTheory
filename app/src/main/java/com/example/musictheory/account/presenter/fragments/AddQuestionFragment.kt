package com.example.musictheory.account.presenter.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.musictheory.R
import com.example.musictheory.account.loginScreen.PersonalAccountFragments
import com.example.musictheory.account.presenter.list.adapter.AnswersAdapter
import com.example.musictheory.account.presenter.list.viewholder.OnAnswerChange
import com.example.musictheory.account.presenter.list.viewholder.OnCheckRightChangeListener
import com.example.musictheory.account.presenter.viewmodels.PersonalAccountViewModel
import com.example.musictheory.core.data.MainActivityCallback
import com.example.musictheory.databinding.FragmentAddQuestionBinding
import com.example.musictheory.trainingtest.data.model.Question
import com.example.musictheory.utils.GeneratedSeedUtils
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.internal.notifyAll
import timber.log.Timber

class AddQuestionFragment : Fragment(), OnCheckRightChangeListener, OnAnswerChange {

    private val personalAccountViewModel: PersonalAccountViewModel
            by hiltNavGraphViewModels<PersonalAccountViewModel>(R.id.nested_personal_account)


    private var _binding : FragmentAddQuestionBinding? = null
    private val binding get() = _binding!!

    lateinit var adapter : AnswersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentAddQuestionBinding.inflate(inflater)

        personalAccountViewModel.setRegister(PersonalAccountFragments.NONE)

        adapter = AnswersAdapter(this, this)
        binding.rvAnswersList.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    personalAccountViewModel.musicTest.collect {
                        Timber.i("t1 collect add quest")
                        lifecycleScope.launch {
//                            Timber.i("t1 col ${personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].questionText}")
//                            Timber.i("t1 size  ${personalAccountViewModel.musicTest.value.questionArray.size}")
                            if(personalAccountViewModel.currentQuestNum.value != -1) {
//                            if(personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].questionText != "Добавить")
                                binding.editTextQuestionAccount.setText(personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].questionText)
                                binding.editTextAttachment.setText(personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].attachmentUrl)
                                binding.editTextGenerationSeed.setText(GeneratedSeedUtils.returnNotes(personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].generationSeed))
                                Timber.i("t1 ${personalAccountViewModel.musicTest.value}")
                                Timber.i("t1 ${GeneratedSeedUtils.returnNotes(personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].generationSeed)}")
                                adapter.updateData(personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].answerArray.toMutableList(),
                                    GeneratedSeedUtils.returnCount(personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].generationSeed["count"].toString()))
                            } else{
                                personalAccountViewModel.setCurrentNum(personalAccountViewModel.musicTest.value.questionArray.size)
                                personalAccountViewModel.musicTest.value.questionArray.add(
                                    Question(
                                        answerArray = mutableListOf(),
                                        attachmentUrl = "",
                                        displayedElements = listOf(),
                                        generationSeed = mutableMapOf(),
                                        questionText = "",
                                        uiType = "none"
                                    )
                                )
                            }
//                            personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].generationSeed?.get("Count") as Int ?:
//                            else binding.editTextQuestionAccount.setText("")
                        }
                    }
                }

            }
        }

        binding.buttonAddAnswer.setOnClickListener {
            personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].answerArray.add("")
            adapter.updateData(personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].answerArray,
                GeneratedSeedUtils.returnCount(personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].generationSeed["count"].toString()))
        }


        return binding.root
    }

    override fun onStop() {
        super.onStop()
        if(personalAccountViewModel.currentQuestNum.value == -1){
            personalAccountViewModel.musicTest.value.questionArray.add(
                Question(
                    answerArray = mutableListOf(),
                    attachmentUrl = "",
                    displayedElements = listOf(),
                    generationSeed = mutableMapOf(),
                    questionText = binding.editTextQuestionAccount.text.toString(),
                    uiType = "none"
                )
            )
        } else {
            if(binding.editTextGenerationSeed.text.toString() == "audio" && !binding.editTextAttachment.text.toString().isNullOrBlank()){
                personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].generationSeed.set(
                    "notes",
                    binding.editTextGenerationSeed.text.toString())
                personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].uiType="audio"
            } else if(binding.editTextGenerationSeed.text.toString() == "audio_notes") {
                personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].uiType="audio"
            }
            else if(!binding.editTextGenerationSeed.text.toString().isNullOrBlank()){
                personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].generationSeed.set(
                    "notes",
                    binding.editTextGenerationSeed.text.toString())
                personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].uiType="stave"
            } else if(!binding.editTextAttachment.text.toString().isNullOrBlank()){
                personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].uiType="picture"
            } else {
                personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].uiType="none"
            }
            personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].let {
                personalAccountViewModel.setQuestion(
                    Question(
                        it.answerArray,
                        binding.editTextAttachment.text.toString(),
                        it.displayedElements,
                        it.generationSeed,
                        binding.editTextQuestionAccount.text.toString(),
                        it.uiType
                    )
                )
            }
        }
//        personalAccountViewModel.setCurrentNum(-1)


//        personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].questionText = ""
    }

    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean, position: Int) {
        Timber.i("t1 count ${personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].generationSeed.get("count")}")
        val prevCount = GeneratedSeedUtils.returnCount(personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].generationSeed["count"].toString())
        if(p1 && position>=prevCount){
            val currentEl = personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].answerArray[position]
            personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].answerArray.remove(currentEl)
            personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].answerArray.add(prevCount, currentEl)
            personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].generationSeed.set("count", prevCount+1)
            Timber.i("t1 countafter ${personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].generationSeed.get("count")}")
            Timber.i("t1 list ${personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].answerArray}")
            adapter.updateData(personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].answerArray,
                GeneratedSeedUtils.returnCount(personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].generationSeed["count"].toString()))
//            viewLifecycleOwner.lifecycleScope.launch {
//                lifecycleScope.launch {
//
//                    personalAccountViewModel.setMusicTest()
//                }
//
//            }



//            personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].generationSeed.get("count")
        } else if(!p1){
            personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].generationSeed.set("count", prevCount-1)
            Timber.i("t1 countafter ${personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].generationSeed.get("count")}")
            adapter.updateData(personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].answerArray,
                GeneratedSeedUtils.returnCount(personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].generationSeed["count"].toString()))
//            viewLifecycleOwner.lifecycleScope.launch {
//                lifecycleScope.launch {
////                    personalAccountViewModel.musicTest.notifyAll()
//
//                    personalAccountViewModel.setMusicTest()
//                }
//
//            }
        }
    }

    override fun onAnswerChange(position: Int, answer: String) {

        personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].answerArray[position] = answer
//        adapter.updateOneRow(answer, position)
//        adapter.updateData(personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].answerArray,
//            GeneratedSeedUtils.returnCount(personalAccountViewModel.musicTest.value.questionArray[personalAccountViewModel.currentQuestNum.value].generationSeed["count"].toString()))
    }


}