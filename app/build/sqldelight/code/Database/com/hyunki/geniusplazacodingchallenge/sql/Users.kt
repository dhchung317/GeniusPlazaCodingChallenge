package com.hyunki.geniusplazacodingchallenge.sql

import kotlin.Long
import kotlin.String

interface Users {
  val _id: Long

  val userId: Long

  val first_name: String

  val last_name: String

  val email: String?

  val avatar: String?

  data class Impl(
    override val _id: Long,
    override val userId: Long,
    override val first_name: String,
    override val last_name: String,
    override val email: String?,
    override val avatar: String?
  ) : Users {
    override fun toString(): String = """
    |Users.Impl [
    |  _id: $_id
    |  userId: $userId
    |  first_name: $first_name
    |  last_name: $last_name
    |  email: $email
    |  avatar: $avatar
    |]
    """.trimMargin()
  }
}
