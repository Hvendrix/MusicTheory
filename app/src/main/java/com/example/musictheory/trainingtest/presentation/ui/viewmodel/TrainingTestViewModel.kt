package com.example.musictheory.trainingtest.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class TrainingTestViewModel : ViewModel() {
    private val _messageHello = MutableStateFlow<String>("Фрагмент тренировочных тестов")
    val messageHello : StateFlow<String> = _messageHello.asStateFlow()

}