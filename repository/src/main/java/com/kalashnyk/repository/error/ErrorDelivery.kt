package com.kalashnyk.repository.error

interface ErrorDelivery {
    fun deliverError(t: Throwable)
}