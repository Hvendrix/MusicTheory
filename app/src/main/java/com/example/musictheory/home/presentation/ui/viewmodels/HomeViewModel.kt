package com.example.musictheory.home.presentation.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musictheory.core.domain.repository.MainRepository
import com.example.musictheory.home.domain.usecases.HomeInteractor
import com.example.musictheory.home.homeRepository.CategoriesRepository
import com.example.musictheory.trainingtest.data.model.MusicTest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class HomeViewModel @Inject constructor(

    private val repository: MainRepository,
) : ViewModel() {

    @Inject
    lateinit var homeInteractor: HomeInteractor

    private val _categories = MutableLiveData<List<MusicTest>>()
    val categories: LiveData<List<MusicTest>> = _categories

    //    private fun getCategories() = viewModelScope.launch {
//        repository.getCategories().let { response ->
//            Timber.v("t1 ${response.body()}")
//            val responseCollection = response.body()
//            if (response.isSuccessful && responseCollection != null)
//                _categories.postValue(responseCollection.data.collection)
//            else
//                Log.d(
//                    "tag",
//                    "getCategories Error: ${response.code()}"
//                )
//        }
//    }
    fun getCategories(token: String) = viewModelScope.launch {
        val response = homeInteractor.getCategories(token)
//        val response = homeInteractor.getCategoriesLocal()
        _categories.postValue(response.data)
    }


    init {
//        getCategories()
    }
}
