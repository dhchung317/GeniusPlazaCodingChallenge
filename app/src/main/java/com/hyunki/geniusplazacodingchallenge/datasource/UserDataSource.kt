package com.hyunki.geniusplazacodingchallenge.datasource

import androidx.paging.PageKeyedDataSource
import com.hyunki.geniusplazacodingchallenge.model.User
import com.hyunki.geniusplazacodingchallenge.repository.UserRepository

class UserDataSource(private val userRepository: UserRepository) :
    PageKeyedDataSource<Int, User>() {
    private var currentPage = 1
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, User>
    ) {
        callback.onResult(
            userRepository.getUsers(currentPage).blockingGet().data, null,
            userRepository.getUsers(currentPage).blockingGet().page + 1
        )
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, User>
    ): Unit =
        callback.onResult(
            userRepository.getUsers(currentPage + 1).blockingGet().data, null
        )

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, User>) = Unit
}