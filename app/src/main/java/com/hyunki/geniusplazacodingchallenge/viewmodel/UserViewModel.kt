package com.hyunki.geniusplazacodingchallenge.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.hyunki.geniusplazacodingchallenge.database.UserDatabaseRepositoryImpl
import com.hyunki.geniusplazacodingchallenge.model.PostUser
import com.hyunki.geniusplazacodingchallenge.model.User
import com.hyunki.geniusplazacodingchallenge.network.UserService
import com.hyunki.geniusplazacodingchallenge.repository.UserRepositoryImpl
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val disposables: CompositeDisposable = CompositeDisposable()
    private val liveData = MutableLiveData<PagedList<User>>()

    private val userRepository = UserRepositoryImpl(UserService.getInstance()!!)
    private val databaseRepository = UserDatabaseRepositoryImpl(application.applicationContext)

    fun addUserToDatabase(user: User) {
        databaseRepository.addUserToDatabase(user)
    }

    fun getUserFromDatabaseById(id: Int): User? {
        return databaseRepository.getUserFromDatabaseById(id)
    }

    fun getUsersFromDatabase() {
    }
    fun getUsersFromNetwork() {

    }

    fun postUser(user: PostUser) {
        disposables.add(
            userRepository.postUser(
                PostUser(
                    first_name = user.first_name,
                    last_name = user.last_name,
                    email = user.email
                )
            ).observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                onError = { t: Throwable? -> Log.d("post-response", t.toString()) },
                onSuccess = { user ->
                    Log.d(
                        "post-response",
                        "${user.email}, ${user.first_name} ${user.last_name}, ${user.id}"
                    )
                    databaseRepository.addUserToDatabase(user)
                }
            )
        )
    }
}