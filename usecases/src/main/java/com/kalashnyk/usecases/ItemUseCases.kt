package com.kalashnyk.usecases

import com.kalashnyk.repository.ItemRepository
import io.reactivex.Completable

interface ItemUseCases {
    fun fetchItems(): Completable
    fun getItemById(id: Long): Completable
    fun getItemByName(name: String): Completable
}

class ItemUseCasesImpl(private val repo: ItemRepository): ItemUseCases {

    override fun fetchItems(): Completable = repo.fetchItems()

    override fun getItemById(id: Long): Completable  = repo.getItemById(id)

    override fun getItemByName(name: String): Completable  = repo.getItemByName(name)

}