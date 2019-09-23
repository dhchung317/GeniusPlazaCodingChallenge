package com.hyunki.geniusplazacodingchallenge.datasource

import androidx.paging.DataSource
import com.hyunki.geniusplazacodingchallenge.model.User

class UserDataSourceFactory (private val dataSource: UserDataSource) :
    DataSource.Factory<Int, User>() {
    override fun create(): DataSource<Int, User> = dataSource
}
