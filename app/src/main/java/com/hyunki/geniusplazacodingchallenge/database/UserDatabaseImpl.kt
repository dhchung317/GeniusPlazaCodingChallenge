package com.hyunki.geniusplazacodingchallenge.database

import android.content.Context
import com.hyunki.geniusplazacodingchallenge.model.User

class UserDatabaseRepositoryImpl(context: Context) : UserDatabaseRepository {
    private val userDatabase = UserDatabase.getInstance(context)

    override fun addUserToDatabase(user: User) {
    }

    override fun getUsersFromDatabase(): List<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserFromDatabaseById(id: Int): User? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}