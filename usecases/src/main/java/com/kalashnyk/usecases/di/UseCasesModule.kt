package com.kalashnyk.usecases.di

import com.kalashnyk.repository.ItemRepository
import com.kalashnyk.usecases.ItemUseCases
import com.kalashnyk.usecases.ItemUseCasesImpl
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {

    @Provides
    internal fun provideItemUseCases(repo: ItemRepository): ItemUseCases =
        ItemUseCasesImpl(repo)
}