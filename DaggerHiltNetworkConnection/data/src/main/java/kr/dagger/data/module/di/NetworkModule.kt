package kr.dagger.data.module.di

import kr.dagger.domain.main.repository.MainRepository
import com.orhanobut.logger.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.dagger.data.ApiData.BASE_URL
import kr.dagger.data.remote.MainRepositoryImpl
import kr.dagger.data.remote.RemoteService
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

//    @Provides
//    @Singleton
//    fun provideIsNetworkAvailable(@ApplicationContext context: Context) : NetworkConnection {
//        return NetworkConnection(context)
//    }

    @Provides
    @Singleton
    fun provideRemoteService(retrofit: Retrofit): RemoteService {
        return retrofit.create(RemoteService::class.java)
    }

    @Provides
    @Singleton
    fun provideMainRepository(remoteService: RemoteService): MainRepository {
        return MainRepositoryImpl(remoteService)
    }

//    @Provides
//    @Singleton
//    fun provideMainRepository(remoteService: RemoteService): MainRepository {
//        return MainRepositoryImpl(remoteService)
//    }
}