package com.dagger.navermapclustering.di

import com.dagger.navermapclustering.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var viewModelModules = module {
    viewModel { MainViewModel(get()) }
}
