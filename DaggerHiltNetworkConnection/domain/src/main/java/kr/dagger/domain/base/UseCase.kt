package kr.dagger.domain.base

interface UseCase<in Params, out T> {
    fun execute(params: Params) : T
}