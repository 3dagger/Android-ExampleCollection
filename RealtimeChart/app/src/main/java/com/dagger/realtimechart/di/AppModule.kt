package com.dagger.realtimechart.di

import com.dagger.realtimechart.Utility
import org.koin.dsl.module

var appModules = module {
	single { Utility(get()) }
}
