package com.dagger.custommarkerview.di

import com.dagger.custommarkerview.RemoteService
import org.koin.dsl.module
import retrofit2.Retrofit

var apiModules = module {
    single {get<Retrofit>().create(RemoteService::class.java)}
}