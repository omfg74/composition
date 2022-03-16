package com.omfgdevelop.composition.presentation.viewModel

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.omfgdevelop.composition.R
import com.omfgdevelop.composition.data.GameRepositoryImpl
import com.omfgdevelop.composition.domain.entity.GameResult
import com.omfgdevelop.composition.domain.entity.GameSettings
import com.omfgdevelop.composition.domain.entity.Level
import com.omfgdevelop.composition.domain.entity.Question
import com.omfgdevelop.composition.domain.repository.GameRepository
import com.omfgdevelop.composition.domain.usecases.GenerateQuestionUseCase
import com.omfgdevelop.composition.domain.usecases.GetGameSettingsUseCase

class GameFragmentViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val MILLS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTE = 60
    }

    private val context = application

    private var timer: CountDownTimer? = null

    private val repository: GameRepository by lazy { GameRepositoryImpl }

    private val _formattedTime = MutableLiveData<String>()

    val formattedTime: LiveData<String>
        get() = _formattedTime

    private val generateQuestionUseCase: GenerateQuestionUseCase =
        GenerateQuestionUseCase(repository)

    private val getGameSettingUseCase = GetGameSettingsUseCase(repository)

    private lateinit var gameSettings: GameSettings

    private lateinit var level: Level

    private val _question: MutableLiveData<Question> = MutableLiveData()

    val question: LiveData<Question>
        get() = _question

    var totalQuestions = 0

    var countOfCorrectAnswers = 0

    private var _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers


    private val _percentOfCorrectAnswers = MutableLiveData<Int>(0)
    val percentOfCorrectAnswers: LiveData<Int>
        get() = _percentOfCorrectAnswers

    private val _enoughCount = MutableLiveData<Boolean>()
    val enoughCount: LiveData<Boolean>
        get() = _enoughCount

    private val _enoughPercent = MutableLiveData<Boolean>()
    val enoughPercent: LiveData<Boolean>
        get() = _enoughPercent

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    fun startGame(level: Level) {
        getGameSettings(level)
        startTimer()
        generateQuestion()
    }

    private fun getGameSettings(level: Level) {
        this.level = level
        this.gameSettings = getGameSettingUseCase(level)
        _minPercent.value = gameSettings.minCountOfRightAnswers
    }

    private fun generateQuestion() {
        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }


    private fun startTimer() {
        timer = object :
            CountDownTimer(gameSettings.gameTimeInSeconds * MILLS_IN_SECONDS, MILLS_IN_SECONDS) {
            override fun onTick(millsToFinish: Long) {
                _formattedTime.value = formatTime(millsToFinish)
            }

            override fun onFinish() {
                stopGame()
            }
        }
        timer?.start()
    }

    private fun formatTime(mills: Long): String {
        val seconds = mills / MILLS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTE
        val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTE)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    private fun stopGame() {
        _gameResult.value =
            GameResult(
                enoughCount.value == true && enoughPercent.value == true,
                countOfCorrectAnswers,
                totalQuestions,
                gameSettings
            )
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    fun onChooseAnswer(text: Int) {
        checkAnswer(text)
        updateProgress()
        generateQuestion()
    }

    private fun updateProgress() {
        val percent = calcPercent()
        _percentOfCorrectAnswers.value = percent
        _progressAnswers.value = context.getString(
            R.string.progress_answers,
            countOfCorrectAnswers.toString(),
            gameSettings.minCountOfRightAnswers.toString()
        )
        _enoughCount.value =
            countOfCorrectAnswers >= gameSettings.minCountOfRightAnswers
        _enoughPercent.value = percent >= gameSettings.minPercentOfRightAnswers

    }

    private fun calcPercent(): Int {
        return ((countOfCorrectAnswers / totalQuestions.toDouble()) * 100).toInt()
    }

    private fun checkAnswer(text: Int) {
        val question = _question.value
        if (question?.correctAnswer?.equals(text) == true) {
            countOfCorrectAnswers++
            _progressAnswers.value = countOfCorrectAnswers.toString()
        }
        totalQuestions++
    }

}