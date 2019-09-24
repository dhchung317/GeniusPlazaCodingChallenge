package com.hyunki.geniusplazacodingchallenge.database.repository

import android.content.Context
import com.hyunki.geniusplazacodingchallenge.database.UserDatabase
import com.hyunki.geniusplazacodingchallenge.model.User

class UserDatabaseRepositoryImpl(context: Context) :
    UserDatabaseRepository {
    private val userDatabase =
        UserDatabase.getInstance(context)

    override fun addUserToDatabase(user: User) {
        userDatabase?.addUser(user)
    }

    override fun getUsersFromDatabase(): List<User> {
        return userDatabase?.getAllUsers()!!
    }

    override fun getUserFromDatabaseById(id:Int): User? {
        return userDatabase?.getUserById(id)
    }

    override fun getEmails(): MutableSet<String?>? {
        return userDatabase?.getEmails()
    }

    override fun databaseSize(): Int {
        return userDatabase?.databaseSize()!!
    }

    override fun checkUserExists(userId: Int): Boolean {
        return userDatabase?.checkUserExists(userId.toLong())!!
    }

    override fun clearDatabase() {
        userDatabase?.clearDatabase()
    }
}