package kr.dagger.pagingdemo.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.dagger.pagingdemo.api.GithubService
import kr.dagger.pagingdemo.db.RepoDatabase
import kr.dagger.pagingdemo.model.Repo


class GithubRepository(
	private val service: GithubService,
	private val database: RepoDatabase
) {

	fun getSearchResultStream(query: String): Flow<PagingData<Repo>> {
		val dbQuery = "%${query.replace(' ', '%')}%"
		val pagingSourceFactory = { database.reposDao().reposByName(dbQuery) }

		@OptIn(ExperimentalPagingApi::class)
		return Pager(
			config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
			remoteMediator = GithubRemoteMediator(
				query,
				service,
				database
			),
			pagingSourceFactory = pagingSourceFactory
		).flow
	}

	companion object {
		const val NETWORK_PAGE_SIZE = 30
	}
}