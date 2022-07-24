package kr.dagger.pagingdemo

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import kr.dagger.pagingdemo.api.GithubService
import kr.dagger.pagingdemo.data.GithubRepository
import kr.dagger.pagingdemo.db.RepoDatabase
import kr.dagger.pagingdemo.ui.ViewModelFactory

object Injection {
	private fun provideGithubRepository(context: Context): GithubRepository {
		return GithubRepository(GithubService.create(), RepoDatabase.getInstance(context))
	}

	fun provideViewModelFactory(context: Context, owner: SavedStateRegistryOwner): ViewModelProvider.Factory {
		return ViewModelFactory(owner, provideGithubRepository(context))
	}
}