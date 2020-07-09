package com.kalashnyk.data_source

import com.kalashnyk.data_source.entity.ItemEntity

interface ItemDataSource {
    fun getAll(): List<ItemEntity>
    fun getItemByName(name: String): ItemEntity
    fun getItemById(id: Long): ItemEntity
}

class ItemDataSourceImpl(private val db: AppDatabase): ItemDataSource {
    override fun getAll(): List<ItemEntity> {
        return db.itemDao().getAll()
    }

    override fun getItemByName(name: String): ItemEntity {
        return db.itemDao().getByName(name)
    }

    override fun getItemById(id: Long): ItemEntity {
        return db.itemDao().getById(id)
    }

}