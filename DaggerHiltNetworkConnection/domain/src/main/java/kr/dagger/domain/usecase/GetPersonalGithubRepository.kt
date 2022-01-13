package kr.dagger.domain.usecase

import kotlinx.coroutines.flow.Flow
import kr.dagger.domain.model.UserRepo
import kr.dagger.domain.repository.MainRepository
import kr.dagger.domain.state.ApiResponse
import javax.inject.Inject

class GetPersonalGithubRepository @Inject constructor(private val mainRepository: MainRepository) {
    fun execute(owner: String): Flow<ApiResponse<List<UserRepo>>> = mainRepository.getUserRepositories(owner)
}