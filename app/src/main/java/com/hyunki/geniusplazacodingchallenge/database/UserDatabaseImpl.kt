package com.hyunki.geniusplazacodingchallenge.database

import android.content.Context
import com.hyunki.geniusplazacodingchallenge.model.User

class UserDatabaseRepositoryImpl(context: Context) : UserDatabaseRepository {
    private val userDatabase = UserDatabase.getInstance(context)

    override fun addUserToDatabase(user: User) {
        userDatabase?.addUser(user)
    }

    override fun getUsersFromDatabase(): List<User> {
        return userDatabase?.getAllUsers()!!
    }

    override fun getUserFromDatabaseById(id:Int): User? {
        return userDatabase?.getUserById(id)
    }
}