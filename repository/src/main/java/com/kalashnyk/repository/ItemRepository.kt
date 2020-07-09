package com.kalashnyk.repository

import io.reactivex.Completable

interface ItemRepository {
    fun fetchItems(): Completable

}

