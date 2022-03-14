package com.omfgdevelop.composition.domain.usecases

import com.omfgdevelop.composition.domain.entity.GameSettings
import com.omfgdevelop.composition.domain.entity.Level
import com.omfgdevelop.composition.domain.repository.GameRepository

class GetGameSettingsUseCase(private val repository: GameRepository) {

    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }
}