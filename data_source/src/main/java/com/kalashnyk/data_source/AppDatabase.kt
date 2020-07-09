package com.kalashnyk.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kalashnyk.data_source.dao.ItemDao
import com.kalashnyk.data_source.entity.ItemEntity

@Database(
    entities = [
        ItemEntity::class
    ], version = 1
)
abstract class AppDatabase(): RoomDatabase() {
    abstract fun itemDao(): ItemDao
}