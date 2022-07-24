package kr.dagger.pagingdemo.ui

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import kr.dagger.pagingdemo.data.GithubRepository

class ViewModelFactory(
	owner: SavedStateRegistryOwner,
	private val repository: GithubRepository
) : AbstractSavedStateViewModelFactory(owner, null) {

	override fun <T : ViewModel?> create(
		key: String,
		modelClass: Class<T>,
		handle: SavedStateHandle
	): T {
		if (modelClass.isAssignableFrom(SearchRepositoriesViewModel::class.java)) {
			return SearchRepositoriesViewModel(repository, handle) as T
		}
		throw IllegalStateException("Unknown ViewModel class")
	}
}