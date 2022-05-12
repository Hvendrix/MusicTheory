package com.example.musictheory.account.presenter.viewmodels

import androidx.lifecycle.ViewModel
import com.example.musictheory.account.domain.usecases.AccountInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddTestViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var accountInteractor: AccountInteractor

//    suspend fun postTestToServer(
//        questionArray: List<String>,
//        answerArray: List<List<String>>,
//        displayedElement: List<String>,
//        testName: String
//    ) {
//        val x = accountInteractor.postTest(questionArray, answerArray, displayedElement, testName)
//    }

    suspend fun postDeleteTest() {
        accountInteractor.postDeleteTest()
    }
}
