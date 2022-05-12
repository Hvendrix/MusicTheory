package com.example.musictheory.account.presenter.viewmodels

import androidx.lifecycle.ViewModel
import com.example.musictheory.account.data.model.ResponseLogin
import com.example.musictheory.account.domain.usecases.AccountInteractor
import com.example.musictheory.account.loginScreen.PersonalAccountFragments
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Response

@HiltViewModel
class PersonalAccountViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var accountInteractor: AccountInteractor

    private val _goRegister = MutableStateFlow<PersonalAccountFragments>(
        PersonalAccountFragments.NONE
    )
    val goRegister: StateFlow<PersonalAccountFragments> = _goRegister.asStateFlow()
    fun setRegister(fragment: PersonalAccountFragments) {
        _goRegister.value = fragment
    }

    private val _serverResponse = MutableStateFlow<ResponseLogin?>(null)
    val serverResponse: StateFlow<ResponseLogin?> = _serverResponse.asStateFlow()

    private val _email = MutableStateFlow<ResponseLogin?>(null)
    val email: StateFlow<ResponseLogin?> = _email.asStateFlow()

    suspend fun postSignUp(
        token: String,
        name: String,
        teacher: Boolean,
        pass: String
    ): Response<ResponseLogin> {
        return accountInteractor.postSignUp(token, name, teacher, pass)
    }

    suspend fun postLogin(token: String, pass: String): Response<ResponseLogin> {
        return accountInteractor.postLogin(token, pass)
    }

    fun setEmail(email: ResponseLogin) {
        _email.value = email
    }

    fun setServerResponse(responseLogin: ResponseLogin) {
        _serverResponse.value = responseLogin
    }

    suspend fun postTestToServer(
        questionArray: List<String>,
        answerArray: List<List<String>>,
        uiType: List<String>,
        testName: String
    ) {
        val x = accountInteractor.postTest(questionArray, answerArray, uiType, testName)
    }

    suspend fun postDeleteTest() {
        accountInteractor.postDeleteTest()
    }
}
