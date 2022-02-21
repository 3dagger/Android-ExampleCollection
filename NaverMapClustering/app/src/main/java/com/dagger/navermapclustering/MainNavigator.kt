package com.dagger.navermapclustering

interface MainNavigator {
	interface View {

	}

	interface ViewModel {
		fun disposableClear()
	}
}