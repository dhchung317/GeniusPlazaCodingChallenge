package com.hyunki.geniusplazacodingchallenge.network.repository

import com.hyunki.geniusplazacodingchallenge.model.PostUser
import com.hyunki.geniusplazacodingchallenge.model.User
import com.hyunki.geniusplazacodingchallenge.model.UserResponse
import com.hyunki.geniusplazacodingchallenge.network.UserService
import io.reactivex.Single

class UserRepositoryImpl(private val service: UserService):UserRepository {

    override fun getUsers(page:Int): Single<UserResponse> {
        return service.getUsers(page)
    }

    override fun postUser(user: PostUser): Single<User> {
        return service.postUser(
            firstName = user.first_name,
            lastName = user.last_name,
            email = user.email)
    }
}