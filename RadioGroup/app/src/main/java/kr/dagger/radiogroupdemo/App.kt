package kr.dagger.radiogroupdemo

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.PrettyFormatStrategy
import java.util.logging.Logger

class App : Application() {
	override fun onCreate() {
		super.onCreate()

		initLogger()
	}

	private fun initLogger() {
		val formatStrategy = PrettyFormatStrategy.newBuilder()
			.showThreadInfo(true) // 쓰레드 보여줄 것인지
			.methodCount(2)        // 몇라인까지 출력할지, 기본값 2
			.methodOffset(5)       // 메서드 오프셋, 기본값 5
			.tag("Leeam")      // 글로벌 태그
			.build()

		com.orhanobut.logger.Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
			override fun isLoggable(priority: Int, tag: String?): Boolean {
				return BuildConfig.DEBUG
			}
		})
	}
}