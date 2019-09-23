package com.hyunki.geniusplazacodingchallenge.database

import com.hyunki.geniusplazacodingchallenge.model.User

interface UserDatabaseRepository {
    fun addUserToDatabase(user: User)
    fun getUsersFromDatabase():List<User>
    fun getUserFromDatabaseById(id : Int):User?
}