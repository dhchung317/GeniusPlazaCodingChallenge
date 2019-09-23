package com.hyunki.geniusplazacodingchallenge.datasource

import androidx.paging.PageKeyedDataSource
import com.hyunki.geniusplazacodingchallenge.database.UserDatabaseRepository
import com.hyunki.geniusplazacodingchallenge.model.User

class DatabaseDataSource(private val userDatabaseRepository: UserDatabaseRepository) :
    PageKeyedDataSource<Int, User>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, User>
    ) {
        callback.onResult(
            userDatabaseRepository.getUsersFromDatabase(), null, null
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, User>) = Unit

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, User>) = Unit
}