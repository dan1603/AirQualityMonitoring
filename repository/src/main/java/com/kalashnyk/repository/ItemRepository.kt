package com.kalashnyk.repository

import com.kalashnyk.data_source.ItemDataSource
import io.reactivex.Completable

interface ItemRepository {
    fun fetchItems(): Completable
    fun getItemById(id: Long): Completable
    fun getItemByName(name: String): Completable
}

class ItemRepositoryImpl(private val itemDataSource: ItemDataSource): ItemRepository {
    override fun fetchItems(): Completable = Completable.fromAction {
        itemDataSource.getAll()
    }

    override fun getItemById(id: Long): Completable = Completable.fromAction {
        itemDataSource.getItemById(id)
    }

    override fun getItemByName(name: String): Completable = Completable.fromAction {
        itemDataSource.getItemByName(name)
    }

}

