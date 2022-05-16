package com.example.musictheory.account.presenter.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musictheory.account.data.model.ResponseLogin
import com.example.musictheory.account.data.model.ResponseToken
import com.example.musictheory.account.data.model.ResponseUser
import com.example.musictheory.account.data.model.UserFlask
import com.example.musictheory.account.domain.usecases.AccountInteractor
import com.example.musictheory.account.loginScreen.PersonalAccountFragments
import com.example.musictheory.trainingtest.data.model.Question
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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

//    private val _token = MutableStateFlow<String>("")
//    val token: StateFlow<String> = _token.asStateFlow()

    private val _serverResponse = MutableStateFlow<ResponseLogin?>(null)
    val serverResponse: StateFlow<ResponseLogin?> = _serverResponse.asStateFlow()

//    private val _email = MutableStateFlow<ResponseLogin?>(null)
//    val email: StateFlow<ResponseLogin?> = _email.asStateFlow()

    private val _user = MutableStateFlow<UserFlask?>(null)
    val user: StateFlow<UserFlask?> = _user.asStateFlow()


    suspend fun postSignUp(
        token: String,
        name: String,
        teacher: Boolean,
        pass: String,
    ): Response<ResponseLogin> {
        return accountInteractor.postSignUp(token, name, teacher, pass)
    }


    suspend fun postSignUpFlask(
        email: String,
        teacher: Boolean,
        pass: String,
    ): Response<ResponseToken> {
        return accountInteractor.postSignUpFlask(email, teacher, pass)
    }

    suspend fun postLogin(token: String, pass: String): Response<ResponseLogin> {
        return accountInteractor.postLogin(token, pass)
    }

    suspend fun postLoginFlask(email: String, password: String): Response<ResponseToken> {
        return accountInteractor.postLoginFlask(email, password)
    }

    suspend fun getUserFlask(token: String): Response<ResponseUser> {
        return accountInteractor.getUserFlask(token)
    }

    fun setEmail(email: ResponseLogin) {
//        _user.value = email
    }

    fun setEmail(email: UserFlask) {
        _user.value = email
    }

//    fun setToken(token: String){
//        _token.value = "Bearer $token"
//    }

    fun setServerResponse(responseLogin: ResponseLogin) {
        _serverResponse.value = responseLogin
    }


    suspend fun loginIn(token: String) {
        viewModelScope.launch {
            val responseUser = async {
                getUserFlask(token)
            }
            val responseUserAwait = responseUser.await().body()?.data
            when {
                responseUserAwait == null -> {
                    setRegister(PersonalAccountFragments.REGISTRATION)
                }
                responseUserAwait.login.isNotEmpty() -> {
                    setEmail(responseUserAwait)
                }
            }
        }
    }


    suspend fun postTestToServer(
        token: String,
        testName: String,
        sectionId: List<String>,
        questionArray: List<Question>,
        teacherId: String,
    ) {
        accountInteractor.postTest(token, testName, sectionId, questionArray, teacherId)
    }

    suspend fun postDeleteTest() {
        accountInteractor.postDeleteTest()
    }
}
