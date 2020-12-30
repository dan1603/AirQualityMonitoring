package com.kalashnyk.repository.di

import com.kalashnyk.data_source.di.DataSourceModule
import com.kalashnyk.repository.ItemRepository
import dagger.Component

@Component(modules = [RepositoryModule::class, DataSourceModule::class])
interface RepositoryComponent {
    val itemRepo: ItemRepository
}