package com.omfgdevelop.composition.domain.usecases

import com.omfgdevelop.composition.domain.entity.Question
import com.omfgdevelop.composition.domain.repository.GameRepository

class GenerateQuestionUseCase(private val repository: GameRepository) {

    private companion object {
        private const val COUNT_OF_OPTIONS = 6;
    }

    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }
}