package com.dagger.navermapclustering

import android.app.Application
import com.dagger.navermapclustering.di.apiModules
import com.dagger.navermapclustering.di.appModules
import com.dagger.navermapclustering.di.networkModules
import com.dagger.navermapclustering.di.viewModelModules
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
	override fun onCreate() {
		super.onCreate()

		startKoin {
			androidContext(this@App)
			modules(listOf(apiModules, appModules, networkModules, viewModelModules))
		}

		// init Logger
		val formatStrategy = PrettyFormatStrategy.newBuilder()
			.showThreadInfo(false) // 쓰레드 보여줄 것인지
			.methodCount(1)        // 몇라인까지 출력할지, 기본값 2
			.methodOffset(5)       // 메서드 오프셋, 기본값 5
			.tag("leeam")      // 글로벌 태그
			.build()

		// 디버그에서만 로그출력
		Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
			override fun isLoggable(priority: Int, tag: String?): Boolean {
				return BuildConfig.DEBUG
			}
		})


	}
}