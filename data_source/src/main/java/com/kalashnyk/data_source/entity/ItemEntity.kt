package com.kalashnyk.data_source.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kalashnyk.data_source.base.BaseModel

@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val name: String
): BaseModel()