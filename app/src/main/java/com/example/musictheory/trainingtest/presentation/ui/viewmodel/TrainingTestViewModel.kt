package com.example.musictheory.trainingtest.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musictheory.core.data.Repository
import com.example.musictheory.home.presentation.model.Id
import com.example.musictheory.model.Result
import com.example.musictheory.trainingtest.data.model.DisplayedElement
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
        listOf(MusicTest(Id(""), "", listOf(),listOf() , "", ""))
    )

//    val serverResponseCollectionList:
//            StateFlow<List<MusicTest>>
//            = _serverResponseCollectionList.asStateFlow()

    private val _serverResponseCollection = MutableStateFlow<MusicTest>(
        MusicTest(Id(""), "", listOf(),listOf() , "", "")
    )
    val serverResponseCollection:
            StateFlow<MusicTest> = _serverResponseCollection.asStateFlow()

    private val _questionString = MutableStateFlow("Вопрос")
    val questionString: StateFlow<String> = _questionString.asStateFlow()

    private val _answersList = MutableStateFlow<List<String>>(
        mutableListOf()
    )
    val answersList: StateFlow<List<String>> = _answersList.asStateFlow()

    private val _uiType = MutableStateFlow("none")
    val uiType: StateFlow<String> = _uiType.asStateFlow()


    private val _displayedElements: MutableStateFlow<List<DisplayedElement>> = MutableStateFlow(listOf())
    val displayedElements: StateFlow<List<DisplayedElement>> = _displayedElements.asStateFlow()

    private val _generationSeed: MutableStateFlow<Map<Any, Any>> = MutableStateFlow(mapOf())
    val generationSeed: StateFlow<Map<Any, Any>> = _generationSeed.asStateFlow()

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

    suspend fun getTests(token: String): ServerResponseMusicTest {
//        _currentQuestionOid.value = "1"
        return trainingTestInteractor.getTests(token)
//        return trainingTestInteractor.getLocalTests2()
    }

    suspend fun postTest() {
//        trainingTestInteractor.postCollection()
//        trainingTestInteractor.postTest()
    }

    fun getData(serverResponse: ServerResponseMusicTest) {
        _serverResponseCollection.value = serverResponse.data[0]
        _serverResponseCollectionList.value = serverResponse.data
        _serverResponseCollectionList.value.forEach {
            if (it.id.oid == currentQuestionOid.value) {
                _serverResponseCollection.value = it
            }
        }
        _currentRightAnswer.value = _serverResponseCollection
            .value.questionArray[_currentQuestionNum.value].answerArray[0]

        _answersList.value = _serverResponseCollection
            .value.questionArray[_currentQuestionNum.value].answerArray.shuffled()


        _uiType.value = _serverResponseCollection
            .value.questionArray[_currentQuestionNum.value].uiType


        _questionString.value = _serverResponseCollection
            .value.questionArray[_currentQuestionNum.value].questionText


        _generationSeed.value = _serverResponseCollection
            .value.questionArray[_currentQuestionNum.value].generationSeed


        _displayedElements.value = defineDisplayedElements(_serverResponseCollection
            .value.questionArray[_currentQuestionNum.value].displayedElements)

        _currentMistakeList.value = mutableListOf()

//        when(_uiType.value){
//            "stave random pick" ->  randomPick()
//        }
        when(_generationSeed.value.get("notes")){
            "from_answers" -> randomPick()
        }





//        _answersList.value = serverResponse.data.collection[0].answerArray[0]
//        _questionString.emit(serverResponse.data.collection[0].questionArray[0])
    }

    fun randomPick(){
        _currentRightAnswer.value = _answersList.value.shuffled()[0]
        _displayedElements.value = defineDisplayedElements2(listOf(_currentRightAnswer.value))
    }

    fun defineDisplayedElements2(noteList: List<String>): List<DisplayedElement>{
        var result = mutableListOf<DisplayedElement>()
        noteList.forEach {
//            var noteName = it.get("nota")
            result.add(when(it){
                "ми" -> DisplayedElement(1f)
                "фа" -> DisplayedElement(1.5f)
                "соль" -> DisplayedElement(2f)
                "ля" -> DisplayedElement(2.5f)
                "си" -> DisplayedElement(3f)
                "до2" -> DisplayedElement(3.5f)
                "ре2" -> DisplayedElement(4f)
                "до" -> DisplayedElement(3.5f)
                "ре" -> DisplayedElement(4f)
                else -> DisplayedElement()
            })
        }
        return result
    }

    fun defineDisplayedElements(noteList: List<Map<String, String>>): List<DisplayedElement>{
        var result = mutableListOf<DisplayedElement>()
        noteList.forEach {
            var noteName = it.get("nota")
            result.add(when(noteName){
                "ми" -> DisplayedElement(1f)
                "фа" -> DisplayedElement(1.5f)
                "соль" -> DisplayedElement(2f)
                "ля" -> DisplayedElement(2.5f)
                "си" -> DisplayedElement(3f)
                "до2" -> DisplayedElement(3.5f)
                "ре2" -> DisplayedElement(4f)
                else -> DisplayedElement()
            })
        }
        return result
    }

    fun goNext() {
        _currentQuestionNum.value = _currentQuestionNum.value + 1
        if (_currentQuestionNum.value + 1 > _serverResponseCollection.value.questionArray.size) {
            saveTest(
                MusicTestEntity(
                    serverResponseCollection.value.id.oid,
                    testName = serverResponseCollection.value.testName,
                    sectionsId = serverResponseCollection.value.sectionsId,
                    questionArray = _serverResponseCollection.value.questionArray,
                    teacherId =serverResponseCollection.value.teacherId,
                    test_id = serverResponseCollection.value.test_id
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
            _currentRightAnswer.value = _serverResponseCollection
                .value.questionArray[_currentQuestionNum.value].answerArray[0]

            _answersList.value = _serverResponseCollection
                .value.questionArray[_currentQuestionNum.value].answerArray.shuffled()

            _questionString.value = _serverResponseCollection
                .value.questionArray[_currentQuestionNum.value].questionText

            _goNextEvent.value = true
        }
    }

    private fun goResult(id: Long) {
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
            _currentMistakeList.value.add(listOf(_serverResponseCollection.value.sectionsId[0]))
        }
        _currentMistakeList.value.add(listOf(answer, _questionString.value))
    }
}
