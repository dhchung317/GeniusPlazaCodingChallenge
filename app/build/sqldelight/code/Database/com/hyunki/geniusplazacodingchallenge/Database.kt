package com.hyunki.geniusplazacodingchallenge

import com.hyunki.geniusplazacodingchallenge.app.newInstance
import com.hyunki.geniusplazacodingchallenge.app.schema
import com.hyunki.geniusplazacodingchallenge.sql.UserQueries
import com.squareup.sqldelight.Transacter
import com.squareup.sqldelight.db.SqlDriver

interface Database : Transacter {
  val userQueries: UserQueries

  companion object {
    val Schema: SqlDriver.Schema
      get() = Database::class.schema

    operator fun invoke(driver: SqlDriver): Database = Database::class.newInstance(driver)}
}
