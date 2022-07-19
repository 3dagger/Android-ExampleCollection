package com.dagger.navermapclustering.di

import com.dagger.navermapclustering.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

var networkModules = module {
    single { Cache(androidApplication().cacheDir, 10L * 1024 * 1024) }

    single<Gson> {
        GsonBuilder().setLenient().create()
    }

    single<OkHttpClient> {
        OkHttpClient.Builder().apply {
            cache(get())
            connectTimeout(10L, TimeUnit.SECONDS)
            writeTimeout(10L, TimeUnit.SECONDS)
            readTimeout(10L, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
            addInterceptor(HttpLoggingInterceptor().apply {
                if (BuildConfig.DEBUG) {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            })
        }.build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://7a7b97f0-1b1a-419e-9591-00f8fba48f4b.mock.pstmn.io/")
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(get())
            .build()
    }
}