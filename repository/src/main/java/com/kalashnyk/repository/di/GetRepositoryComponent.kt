package com.kalashnyk.repository.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.kalashnyk.data_source.AppDatabase
import com.kalashnyk.data_source.di.DataSourceModule

object GetRepositoryComponent {
    fun execute(app: Application): RepositoryComponent {
        return DaggerRepositoryComponent.builder()
            .dataSourceModule(DataSourceModule(getAppDatabase(app.applicationContext)))
            .repositoryModule(RepositoryModule())
            .build()
    }

    private fun getAppDatabase(context: Context) = Room.databaseBuilder(context, AppDatabase::class.java, "airqualitymonitoring_db")
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

}