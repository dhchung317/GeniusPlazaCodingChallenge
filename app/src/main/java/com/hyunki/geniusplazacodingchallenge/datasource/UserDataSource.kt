package com.hyunki.geniusplazacodingchallenge.datasource

import androidx.paging.PageKeyedDataSource
import com.hyunki.geniusplazacodingchallenge.model.User
import com.hyunki.geniusplazacodingchallenge.network.repository.UserRepository

class UserDataSource(private val userRepository: UserRepository) :
    PageKeyedDataSource<Int, User>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, User>
    ) {
        callback.onResult(
            userRepository.getUsers(1).blockingGet().data, null,2
        )
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, User>
    ): Unit =
        callback.onResult(
            userRepository.getUsers(2).blockingGet().data, null
        )

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, User>) = Unit
}