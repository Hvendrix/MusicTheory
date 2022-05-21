package com.example.musictheory.trainingtest.presentation.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musictheory.core.data.Repository
import com.example.musictheory.core.data.model.Result
import com.example.musictheory.trainingtest.data.model.MusicTestEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _result: MutableLiveData<Result> = MutableLiveData()
    val result: LiveData<Result>
        get() = _result

    private val _test: MutableLiveData<MusicTestEntity> = MutableLiveData()
    val test: LiveData<MusicTestEntity>
        get() = _test

//    fun saveResult(result: Result) {
//        viewModelScope.launch {
//            repository.local.saveResult(result)
//        }
//    }
//
//    fun saveTest(test: Test) {
//        viewModelScope.launch {
//            repository.local.saveTest(test)
//        }
//    }

    fun getResultById(id: Long) {
        viewModelScope.launch {
            repository.local.getResultById(id).collect { results ->
                if (results != null && results.isNotEmpty()) {
                    _result.postValue(results[0])
                }
            }
        }
    }

    fun getTestById(id: String) {
        viewModelScope.launch {
            repository.local.getTestById(id).collect { tests ->
                _test.postValue(tests[0])
            }
        }
    }
}
