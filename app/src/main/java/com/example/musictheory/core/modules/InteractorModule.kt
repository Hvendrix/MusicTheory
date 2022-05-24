package com.example.musictheory.core.modules

import com.example.musictheory.account.domain.usecases.AccountInteractor
import com.example.musictheory.core.domain.repository.MainRepository
import com.example.musictheory.home.domain.usecases.HomeInteractor
import com.example.musictheory.trainingtest.domain.usecases.TrainingTestInteractor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Владислав Хвесюк  08.11.2021
 */

@Module
@InstallIn(SingletonComponent::class)
class InteractorModule @Inject constructor() {
    /**
     * Интерактор, который содержит use cases для обращения из presentation слоя
     */
    @Provides
    @Singleton
    fun provideTrainingTestInteractor(
        mainRepository: MainRepository
    ): TrainingTestInteractor = TrainingTestInteractor(mainRepository)

    @Provides
    @Singleton
    fun provideHomeInteractor(
        mainRepository: MainRepository
    ): HomeInteractor = HomeInteractor(mainRepository)

    @Provides
    @Singleton
    fun provideAccountInteractor(
        mainRepository: MainRepository
    ): AccountInteractor = AccountInteractor(mainRepository)
}
