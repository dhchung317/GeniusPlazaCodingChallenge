package com.hyunki.geniusplazacodingchallenge.database.repository

import com.hyunki.geniusplazacodingchallenge.model.User
import com.hyunki.geniusplazacodingchallenge.sql.SelectEmails
import io.reactivex.Single

interface UserDatabaseRepository {
    fun addUserToDatabase(user: User)
    fun getUsersFromDatabase():List<User>
    fun getUserFromDatabaseById(id : Int):User?
    fun getEmails(): Single<MutableSet<String?>>
    fun checkUserExists(userId: Int):Boolean
    fun databaseSize():Int
    fun clearDatabase()
}