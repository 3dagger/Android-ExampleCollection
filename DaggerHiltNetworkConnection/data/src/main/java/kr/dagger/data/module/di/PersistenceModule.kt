package kr.dagger.data.module.di

//import com.dagger.daggerhiltnetworkconnection.persistence.TypeResponseConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

//    @Provides
//    @Singleton
//    fun provideTypeResponseConverter(moshi: Moshi): TypeResponseConverter {
//        return TypeResponseConverter(moshi)
//    }
}