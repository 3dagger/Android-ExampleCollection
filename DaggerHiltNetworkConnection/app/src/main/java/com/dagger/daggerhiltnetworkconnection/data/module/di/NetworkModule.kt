package com.dagger.daggerhiltnetworkconnection.data.module.di

import android.content.Context
import com.dagger.daggerhiltnetworkconnection.BuildConfig
import com.dagger.daggerhiltnetworkconnection.data.ApiData.BASE_URL
import com.dagger.daggerhiltnetworkconnection.data.remote.MainRepositoryImpl
import com.dagger.daggerhiltnetworkconnection.domain.main.repository.MainRepository
import com.dagger.daggerhiltnetworkconnection.data.remote.RemoteService
import com.dagger.daggerhiltnetworkconnection.data.mapper.ResponseMapper
import com.dagger.daggerhiltnetworkconnection.data.network.retrofit.factory.ResponseAdapterFactory
import com.dagger.daggerhiltnetworkconnection.utils.NetworkConnection
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                if(BuildConfig.DEBUG) level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
//            .addCallAdapterFactory(ResponseAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideIsNetworkAvailable(@ApplicationContext context: Context) : NetworkConnection {
        return NetworkConnection(context)
    }

    @Provides
    @Singleton
    fun provideRemoteService(retrofit: Retrofit): RemoteService {
        return retrofit.create(RemoteService::class.java)
    }

    @Provides
    @Singleton
    fun provideMainRepository(remoteService: RemoteService): MainRepository {
//        return MainRepositoryImpl(remoteService, ResponseMapper::responseToUserInfo)
        return MainRepositoryImpl(remoteService)
    }
}