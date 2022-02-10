package com.dagger.realtimechart

import android.app.Application
import com.dagger.realtimechart.di.apiModules
import com.dagger.realtimechart.di.networkModules
import com.dagger.realtimechart.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RealtimeChartApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@RealtimeChartApp)
            modules(listOf(apiModules, networkModules, viewModelModules))
        }
    }
}