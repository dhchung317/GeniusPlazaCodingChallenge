package com.hyunki.geniusplazacodingchallenge.database

import android.content.Context
import android.util.Log
import com.hyunki.geniusplazacodingchallenge.Database
import com.hyunki.geniusplazacodingchallenge.model.User
import com.hyunki.geniusplazacodingchallenge.util.AutoIncrementUtil
import com.squareup.sqldelight.android.AndroidSqliteDriver
import io.reactivex.Single

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

    fun addUser(user:User){
        val entryId = database.usersQueries.selectAllUsers().executeAsList().size
        database.usersQueries.insertOrReplaceUser(
            AutoIncrementUtil.getAutoIncrement(entryId).toLong(),
            user.id.toLong(),
            user.first_name,
            user.last_name,
            user.email,
            user.avatar
        )
    }

    fun getAllUsers(): List<User>{
        val users = database.usersQueries.selectAllUsers()
        val userList : ArrayList<User> = ArrayList()
        for(u in users.executeAsList()){
            Log.d("database get all", "${u.first_name} ${u.userId}")
            userList.add(User(
                id = u.userId.toInt(),
                first_name = u.first_name,
                last_name = u.last_name,
                email = u.email,
                avatar = u.avatar
            ))
        }
        return userList
    }

    fun getUserById(id:Int):User{
        val userQuery = database.usersQueries.selectUserById(id.toLong()).executeAsOne()
        return User(id = userQuery.userId.toInt(),
            first_name = userQuery.first_name,
            last_name = userQuery.last_name,
            email = userQuery.email,
            avatar = userQuery.avatar)
    }

    fun getEmails(): Single<MutableSet<String?>> {
        return Single.just(
            database.usersQueries.selectEmails().executeAsList().map {
                    selectEmails -> selectEmails.email }.toMutableSet()
        )
    }

    fun databaseSize():Int{
        return database.usersQueries.selectAllUsers().executeAsList().size
    }

    fun checkUserExists(userId:Long): Boolean {
        return database.usersQueries.checkExists(userId).executeAsOne()
    }

    fun clearDatabase(){
        database.usersQueries.deleteAllUsers()
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