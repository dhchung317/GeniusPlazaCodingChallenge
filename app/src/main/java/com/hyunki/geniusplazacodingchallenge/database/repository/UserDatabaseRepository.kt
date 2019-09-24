package com.hyunki.geniusplazacodingchallenge.database.repository

import com.hyunki.geniusplazacodingchallenge.model.User
import com.hyunki.geniusplazacodingchallenge.sql.SelectEmails

interface UserDatabaseRepository {
    fun addUserToDatabase(user: User)
    fun getUsersFromDatabase():List<User>
    fun getUserFromDatabaseById(id : Int):User?
    fun getEmails(): MutableSet<String?>?
    fun checkUserExists(userId: Int):Boolean
    fun databaseSize():Int
    fun clearDatabase()
}