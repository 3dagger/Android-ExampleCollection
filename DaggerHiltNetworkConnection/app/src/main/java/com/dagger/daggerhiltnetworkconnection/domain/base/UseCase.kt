package com.dagger.daggerhiltnetworkconnection.domain.base

interface UseCase<in Params, out T> {
    fun execute(params: Params) : T
}