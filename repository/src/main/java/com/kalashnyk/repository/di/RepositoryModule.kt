package com.kalashnyk.repository.di

import com.kalashnyk.data_source.ItemDataSource
import com.kalashnyk.repository.ItemRepository
import com.kalashnyk.repository.ItemRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    internal fun provideItemRepository(
        itemDataSource: ItemDataSource
    ): ItemRepository = ItemRepositoryImpl(itemDataSource)
}