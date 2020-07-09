package com.kalashnyk.data_source.di

import com.kalashnyk.data_source.AppDatabase
import com.kalashnyk.data_source.ItemDataSource
import com.kalashnyk.data_source.ItemDataSourceImpl
import dagger.Module
import dagger.Provides

@Module
class DataSourceModule(private val db: AppDatabase) {

    @Provides
    internal fun provideAppDatabase(): AppDatabase = db

    @Provides
    internal fun provideItemDataSource(db: AppDatabase): ItemDataSource =
        ItemDataSourceImpl(db)

}