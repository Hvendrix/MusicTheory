package com.example.musictheory.trainingtest.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musictheory.core.data.Repository
import com.example.musictheory.home.homeModel.Id
import com.example.musictheory.model.Result
import com.example.musictheory.trainingtest.data.model.MusicTest
import com.example.musictheory.trainingtest.data.model.MusicTestEntity
import com.example.musictheory.trainingtest.data.model.ServerResponseMusicTest
import com.example.musictheory.trainingtest.domain.usecases.TrainingTestInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class TrainingTestViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {
    /**
     * Интерактор для взаимодействия с репозиторием
     *
     * Провайдер указан в InteractorModule
     *
     * @see com.example.musictheory.core.modules.InteractorModule
     */
    @Inject
    lateinit var trainingTestInteractor: TrainingTestInteractor

    private val _messageHello = MutableStateFlow("Фрагмент тренировочных тестов")
    val messageHello: StateFlow<String> = _messageHello.asStateFlow()

    private val _currentQuestionOid = MutableStateFlow("")
    val currentQuestionOid: StateFlow<String> = _currentQuestionOid.asStateFlow()

    private val _serverResponseCollectionList = MutableStateFlow<List<MusicTest>>(
        listOf(MusicTest(Id(""), "", listOf(), listOf(), listOf(), ""))
    )

//    val serverResponseCollectionList:
//            StateFlow<List<MusicTest>>
//            = _serverResponseCollectionList.asStateFlow()

    private val _serverResponseCollection = MutableStateFlow<MusicTest>(
        MusicTest(Id(""), "", listOf(), listOf(), listOf(), "")
    )
    val serverResponseCollection:
        StateFlow<MusicTest> = _serverResponseCollection.asStateFlow()

    private val _questionString = MutableStateFlow("Вопрос")
    val questionString: StateFlow<String> = _questionString.asStateFlow()

    private val _answersList = MutableStateFlow<List<String>>(
        mutableListOf()
    )
    val answersList: StateFlow<List<String>> = _answersList.asStateFlow()

    private val _displayedElements = MutableStateFlow("none")
    val displayedElements: StateFlow<String> = _displayedElements.asStateFlow()

    private val _goNextEvent = MutableStateFlow<Boolean>(false)
    val goNextEvent: StateFlow<Boolean> = _goNextEvent.asStateFlow()

    private val _goResultEvent = MutableStateFlow(0L)
    val goResultEvent: StateFlow<Long> = _goResultEvent.asStateFlow()

    private val _currentQuestionNum = MutableStateFlow(0)
    val currentQuestionNum: StateFlow<Int> = _currentQuestionNum.asStateFlow()

    private val _currentRightAnswer = MutableStateFlow("")
    val currentRightAnswer: StateFlow<String> = _currentRightAnswer.asStateFlow()

    private val _currentMistakeList = MutableStateFlow<MutableList<List<String>>>(
        mutableListOf()
    )
    val currentMistakeList:
        StateFlow<MutableList<List<String>>> = _currentMistakeList.asStateFlow()

    /**
     * Получаем данные через интерактор
     */

    fun setOid(oid: String) {
        _currentQuestionOid.value = oid
    }

    suspend fun getTests(): ServerResponseMusicTest {
        return trainingTestInteractor.getTests()
    }

    suspend fun postTest() {
//        trainingTestInteractor.postCollection()
//        trainingTestInteractor.postTest()
    }

    fun getData(serverResponse: ServerResponseMusicTest) {
        _serverResponseCollection.value = serverResponse.data.collection[0]
        _serverResponseCollectionList.value = serverResponse.data.collection
        _serverResponseCollectionList.value.forEach {
            if (it.id.oid == currentQuestionOid.value) {
                _serverResponseCollection.value = it
            }
        }
        _currentRightAnswer.value = _serverResponseCollection
            .value.answerArray[_currentQuestionNum.value][0]

        _answersList.value = _serverResponseCollection
            .value.answerArray[_currentQuestionNum.value].shuffled()

        _questionString.value = _serverResponseCollection
            .value.questionArray[_currentQuestionNum.value]

        _currentMistakeList.value = mutableListOf()

//        _answersList.value = serverResponse.data.collection[0].answerArray[0]
//        _questionString.emit(serverResponse.data.collection[0].questionArray[0])
    }

    fun goNext() {
        _currentQuestionNum.value = _currentQuestionNum.value + 1
        if (_currentQuestionNum.value + 1 > _serverResponseCollection.value.questionArray.size) {
            saveTest(
                MusicTestEntity(
                    serverResponseCollection.value.id.oid,
                    sectionsId = "0",
                    questionArray = _serverResponseCollection.value.questionArray,
                    answerArray = _serverResponseCollection.value.answerArray,
                    displayedElements = _serverResponseCollection.value.displayedElements
                )
            )
            viewModelScope.launch {
                val id = withContext(Dispatchers.IO) {
                    var mistakeCountNotEmpty = 0
                    if (currentMistakeList.value.isNotEmpty()) {
                        mistakeCountNotEmpty = -1
                    }
                    saveResult(
                        Result(
                            idTest = serverResponseCollection.value.id.oid,
                            mistakeCount = currentMistakeList.value.size + mistakeCountNotEmpty,
                            mistakeArray = currentMistakeList.value
                        )
                    )
                }
                goResult(id)
            }
            return
        } else {
            _answersList.value = _serverResponseCollection
                .value.answerArray[_currentQuestionNum.value].shuffled()

            _currentRightAnswer.value = _serverResponseCollection
                .value.answerArray[_currentQuestionNum.value][0]

            _questionString.value = _serverResponseCollection
                .value.questionArray[_currentQuestionNum.value]

            _goNextEvent.value = true
        }
    }

    fun goResult(id: Long) {
        _goResultEvent.value = id
    }

    suspend fun saveResult(result: Result): Long {
        return viewModelScope.async {
            return@async repository.local.saveResult(result)
        }.await()
    }

    fun saveTest(test: MusicTestEntity) {
        viewModelScope.launch {
            repository.local.saveTest(test)
        }
    }

    fun setMistake(answer: String) {
        if (currentMistakeList.value.isEmpty()) {
            _currentMistakeList.value.add(listOf(_serverResponseCollection.value.sectionsId))
        }
        _currentMistakeList.value.add(listOf(answer, _questionString.value))
    }
}
