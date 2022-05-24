package com.example.musictheory

import androidx.lifecycle.ViewModel
import com.example.musictheory.account.data.model.UserFlask
import com.example.musictheory.account.domain.usecases.AccountInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var accountInteractor: AccountInteractor

    private val _token = MutableStateFlow<String>("")
    val token: StateFlow<String> = _token.asStateFlow()

    private val _user = MutableStateFlow<UserFlask?>(null)
    val user: StateFlow<UserFlask?> = _user.asStateFlow()

    fun setToken(token: String){
        if(token.isNotEmpty())
        _token.value = "Bearer $token"
        else _token.value = ""
    }

    fun setUser(email: UserFlask?) {
        _user.value = email
    }
}