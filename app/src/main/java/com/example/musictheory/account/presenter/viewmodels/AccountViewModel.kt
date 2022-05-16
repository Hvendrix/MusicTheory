package com.example.musictheory.account.presenter.viewmodels

import androidx.lifecycle.ViewModel
import com.example.musictheory.account.domain.usecases.AccountInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var accountInteractor: AccountInteractor

    suspend fun postTestToServer(
        questionArray: List<String>,
        answerArray: List<List<String>>,
        uiType: List<String>,
        testName: String
    ) {
//        val x = accountInteractor.postTest(questionArray, answerArray, uiType, testName)
//        Timber.i("t10  value ${x.isSuccessful}  ${x.body()}   ${x.errorBody() } ${x.message()}  ${x.code()}")
    }
}
