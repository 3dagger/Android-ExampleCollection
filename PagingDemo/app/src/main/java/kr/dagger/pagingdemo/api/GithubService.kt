package kr.dagger.pagingdemo.api

import kr.dagger.pagingdemo.api.response.RepoSearchResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

	@GET("search/repositories?sort=stars")
	suspend fun searchRepos(
		@Query("q") query: String,
		@Query("page") page: Int,
		@Query("per_page") itemsPerPage: Int
	) : RepoSearchResponse

	companion object {
		private const val BASE_URL = "https://api.github.com/"

		fun create(): GithubService {
			val loggingInterceptor = HttpLoggingInterceptor()
			loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

			val client = OkHttpClient.Builder()
				.addInterceptor(loggingInterceptor)
				.build()
			return Retrofit.Builder()
				.baseUrl(BASE_URL)
				.client(client)
				.addConverterFactory(GsonConverterFactory.create())
				.build()
				.create(GithubService::class.java)
		}
	}

}