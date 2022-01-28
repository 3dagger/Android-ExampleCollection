package com.dagger.custommarkerview.di

import com.dagger.custommarkerview.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


var viewModelModules = module {
    viewModel { MainViewModel(get()) }
}
