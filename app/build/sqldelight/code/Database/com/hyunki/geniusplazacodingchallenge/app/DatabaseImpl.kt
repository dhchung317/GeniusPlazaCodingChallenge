package com.hyunki.geniusplazacodingchallenge.app

import com.hyunki.geniusplazacodingchallenge.Database
import com.hyunki.geniusplazacodingchallenge.sql.UserQueries
import com.hyunki.geniusplazacodingchallenge.sql.Users
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.TransacterImpl
import com.squareup.sqldelight.db.SqlCursor
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.internal.copyOnWriteList
import kotlin.Any
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.collections.MutableList
import kotlin.reflect.KClass

internal val KClass<Database>.schema: SqlDriver.Schema
  get() = DatabaseImpl.Schema

internal fun KClass<Database>.newInstance(driver: SqlDriver): Database = DatabaseImpl(driver)

private class DatabaseImpl(
  driver: SqlDriver
) : TransacterImpl(driver), Database {
  override val userQueries: UserQueriesImpl = UserQueriesImpl(this, driver)

  object Schema : SqlDriver.Schema {
    override val version: Int
      get() = 1

    override fun create(driver: SqlDriver) {
      driver.execute(null, """
          |CREATE TABLE Users (
          |    _id INTEGER PRIMARY KEY NOT NULL,
          |    userId INTEGER UNIQUE NOT NULL,
          |    first_name TEXT NOT NULL,
          |    last_name TEXT NOT NULL,
          |    email TEXT,
          |    avatar TEXT
          |)
          """.trimMargin(), 0)
    }

    override fun migrate(
      driver: SqlDriver,
      oldVersion: Int,
      newVersion: Int
    ) {
    }
  }
}

private class UserQueriesImpl(
  private val database: DatabaseImpl,
  private val driver: SqlDriver
) : TransacterImpl(driver), UserQueries {
  internal val selectAllUsers: MutableList<Query<*>> = copyOnWriteList()

  internal val selectUserById: MutableList<Query<*>> = copyOnWriteList()

  override fun <T : Any> selectAllUsers(mapper: (
    _id: Long,
    userId: Long,
    first_name: String,
    last_name: String,
    email: String?,
    avatar: String?
  ) -> T): Query<T> = Query(-1690436615, selectAllUsers, driver, "User.sq", "selectAllUsers",
      "SELECT * FROM Users") { cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getLong(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!,
      cursor.getString(4),
      cursor.getString(5)
    )
  }

  override fun selectAllUsers(): Query<Users> = selectAllUsers(Users::Impl)

  override fun <T : Any> selectUserById(userId: Long, mapper: (
    _id: Long,
    userId: Long,
    first_name: String,
    last_name: String,
    email: String?,
    avatar: String?
  ) -> T): Query<T> = SelectUserById(userId) { cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getLong(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!,
      cursor.getString(4),
      cursor.getString(5)
    )
  }

  override fun selectUserById(userId: Long): Query<Users> = selectUserById(userId, Users::Impl)

  override fun insertOrReplaceUser(
    _id: Long?,
    userId: Long,
    first_name: String,
    last_name: String,
    email: String?,
    avatar: String?
  ) {
    driver.execute(357778509, """
    |INSERT OR REPLACE INTO Users(
    |    _id,
    |    userId,
    |    first_name,
    |    last_name,
    |    email,
    |    avatar
    |)
    |VALUES(?1,?2,?3,?4,?5,?6)
    """.trimMargin(), 6) {
      bindLong(1, _id)
      bindLong(2, userId)
      bindString(3, first_name)
      bindString(4, last_name)
      bindString(5, email)
      bindString(6, avatar)
    }
    notifyQueries(357778509, {database.userQueries.selectAllUsers +
        database.userQueries.selectUserById})
  }

  override fun deleteAllUsers() {
    driver.execute(-400740952, """DELETE FROM Users""", 0)
    notifyQueries(-400740952, {database.userQueries.selectAllUsers +
        database.userQueries.selectUserById})
  }

  private inner class SelectUserById<out T : Any>(
    private val userId: Long,
    mapper: (SqlCursor) -> T
  ) : Query<T>(selectUserById, mapper) {
    override fun execute(): SqlCursor = driver.executeQuery(548526415,
        """SELECT * FROM Users WHERE userId = ?1""", 1) {
      bindLong(1, userId)
    }

    override fun toString(): String = "User.sq:selectUserById"
  }
}
