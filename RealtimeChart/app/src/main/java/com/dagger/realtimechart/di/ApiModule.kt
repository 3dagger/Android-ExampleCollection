package com.dagger.realtimechart.di

import com.dagger.realtimechart.RemoteService
import org.koin.dsl.module
import retrofit2.Retrofit

var apiModules = module {
    single { get<Retrofit>().create(RemoteService::class.java) }
}