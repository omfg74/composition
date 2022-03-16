package com.omfgdevelop.composition.domain.repository

import com.omfgdevelop.composition.domain.entity.GameSettings
import com.omfgdevelop.composition.domain.entity.Level
import com.omfgdevelop.composition.domain.entity.Question

interface GameRepository {

    fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question

    fun getGameSettings(level: Level): GameSettings
}