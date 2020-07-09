package com.kalashnyk.data_source.dao

import androidx.room.Dao
import androidx.room.Query
import com.kalashnyk.data_source.entity.ItemEntity

@Dao
interface ItemDao {

    @Query("SELECT * FROM items")
    fun getAll(): List<ItemEntity>

    @Query("SELECT * FROM items WHERE id = :id")
    fun getById(id: Long): ItemEntity

    @Query("SELECT * FROM items WHERE name = :name")
    fun getByName(name: String): ItemEntity

}