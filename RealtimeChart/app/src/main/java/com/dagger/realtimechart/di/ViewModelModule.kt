package com.dagger.realtimechart.di

import com.dagger.realtimechart.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var viewModelModules = module {
    viewModel { MainViewModel(get()) }
}
