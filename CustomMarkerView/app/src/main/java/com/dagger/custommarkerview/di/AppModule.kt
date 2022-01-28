package com.dagger.custommarkerview.di

import com.dagger.custommarkerview.MainFragment
import org.koin.dsl.module

var appModules = module {
    single { MainFragment() }
}
