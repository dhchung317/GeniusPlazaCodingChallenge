package com.hyunki.geniusplazacodingchallenge.network.repository

import com.hyunki.geniusplazacodingchallenge.model.PostUser
import com.hyunki.geniusplazacodingchallenge.model.User
import com.hyunki.geniusplazacodingchallenge.model.UserResponse
import io.reactivex.Single

interface UserRepository {
    fun getUsers(page:Int): Single<UserResponse>
    fun postUser(user: PostUser): Single<User>
}