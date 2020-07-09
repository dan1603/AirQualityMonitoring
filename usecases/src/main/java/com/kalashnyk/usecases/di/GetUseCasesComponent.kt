package com.kalashnyk.usecases.di

import android.app.Application
import com.kalashnyk.repository.di.GetRepositoryComponent

object GetUseCasesComponent {
    fun execute(app: Application): UseCasesComponent {
        return DaggerUseCasesComponent.builder()
            .repositoryComponent(GetRepositoryComponent.execute(app))
            .useCasesModule(UseCasesModule())
            .build()
    }
}