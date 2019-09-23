package com.hyunki.geniusplazacodingchallenge.network

import com.hyunki.geniusplazacodingchallenge.model.User
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface UserService {
    @GET(URL)
    fun getUsers(@Query("page") page : Int): Single<UserResponse>

    @POST(URL)
    @FormUrlEncoded
    fun postUser(@Field("first_name") firstName:String,
                 @Field("last_name") lastName:String,
                 @Field("email") email: String?
    ): Single<User>

    companion object Factory {
        private const val URL = "api/users"
        private const val BASE_URL = "https://reqres.in"

        @Volatile
        private var retrofit: Retrofit? = null

        @Synchronized
        fun getInstance() : UserService? {
            retrofit?: synchronized(this){
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .build()
            }
            return retrofit?.create(UserService::class.java)
        }
    }
}