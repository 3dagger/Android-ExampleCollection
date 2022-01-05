package com.dagger.daggerhiltnetworkconnection.domain.base

interface SuspendUseCase<in Params, out T> {
    suspend fun execute(params: Params) : T
}