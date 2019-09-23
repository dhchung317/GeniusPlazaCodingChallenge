package com.hyunki.geniusplazacodingchallenge.sql

import com.squareup.sqldelight.Query
import com.squareup.sqldelight.Transacter
import kotlin.Any
import kotlin.Long
import kotlin.String

interface UserQueries : Transacter {
  fun <T : Any> selectAllUsers(mapper: (
    _id: Long,
    userId: Long,
    first_name: String,
    last_name: String,
    email: String?,
    avatar: String?
  ) -> T): Query<T>

  fun selectAllUsers(): Query<Users>

  fun <T : Any> selectUserById(userId: Long, mapper: (
    _id: Long,
    userId: Long,
    first_name: String,
    last_name: String,
    email: String?,
    avatar: String?
  ) -> T): Query<T>

  fun selectUserById(userId: Long): Query<Users>

  fun insertOrReplaceUser(
    userId: Long,
    first_name: String,
    last_name: String,
    email: String?,
    avatar: String?
  )

  fun deleteAllUsers()
}
