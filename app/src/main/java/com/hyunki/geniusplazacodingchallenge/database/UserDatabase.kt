package com.hyunki.geniusplazacodingchallenge.database

import android.content.Context
import com.hyunki.geniusplazacodingchallenge.Database
import com.squareup.sqldelight.android.AndroidSqliteDriver

class UserDatabase(context: Context) {
    private val database = makeDatabase(context)

    private fun makeDatabase(context: Context):Database{
        return Database.invoke(
            AndroidSqliteDriver(
                Database.Schema,
                context,
                "Users.db"
            )
        )
    }

    companion object Factory {
        @Volatile
        private var userDatabase: UserDatabase? = null

        @Synchronized
        fun getInstance(context: Context): UserDatabase? {
            userDatabase ?: synchronized(this) {
                userDatabase = UserDatabase(context)
            }
            return userDatabase
        }
    }
}