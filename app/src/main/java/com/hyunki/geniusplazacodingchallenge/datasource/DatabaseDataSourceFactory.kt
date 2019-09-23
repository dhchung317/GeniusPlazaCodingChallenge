package com.hyunki.geniusplazacodingchallenge.datasource

import androidx.paging.DataSource
import com.hyunki.geniusplazacodingchallenge.model.User

class DatabaseSourceFactory (private val dbDataSource: DatabaseDataSource) :
    DataSource.Factory<Int, User>() {
    override fun create(): DataSource<Int, User> = dbDataSource
}