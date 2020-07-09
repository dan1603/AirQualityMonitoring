package com.kalashnyk.usecases.di

import com.kalashnyk.repository.di.RepositoryComponent
import com.kalashnyk.usecases.ItemUseCases
import dagger.Component

@Component(modules = [UseCasesModule::class], dependencies = [RepositoryComponent::class])
interface UseCasesComponent {
    val itemUseCases: ItemUseCases
}