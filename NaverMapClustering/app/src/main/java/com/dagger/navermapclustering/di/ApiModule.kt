package com.dagger.navermapclustering.di

import com.dagger.navermapclustering.RemoteService
import org.koin.dsl.module
import retrofit2.Retrofit

var apiModules = module {
    single { get<Retrofit>().create(RemoteService::class.java) }
}