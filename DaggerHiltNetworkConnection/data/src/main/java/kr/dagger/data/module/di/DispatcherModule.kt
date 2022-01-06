package kr.dagger.data.module.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
//
//    @Provides
//    @Singleton
//    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Singleton
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

//    @Provides
//    @Singleton
//    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main




//    @Provides
//    @Singleton
//    fun provideIoDispatcher(): CoroutineDispatcher {
//        return Dispatchers.IO
//    }
}