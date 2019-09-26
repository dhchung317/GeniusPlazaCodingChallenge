package com.hyunki.geniusplazacodingchallenge.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.hyunki.geniusplazacodingchallenge.database.repository.UserDatabaseRepositoryImpl
import com.hyunki.geniusplazacodingchallenge.datasource.DatabaseDataSource
import com.hyunki.geniusplazacodingchallenge.datasource.DatabaseSourceFactory
import com.hyunki.geniusplazacodingchallenge.datasource.UserDataSource
import com.hyunki.geniusplazacodingchallenge.datasource.UserDataSourceFactory
import com.hyunki.geniusplazacodingchallenge.model.PostUser
import com.hyunki.geniusplazacodingchallenge.model.User
import com.hyunki.geniusplazacodingchallenge.network.UserService
import com.hyunki.geniusplazacodingchallenge.network.repository.UserRepositoryImpl
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val disposables: CompositeDisposable = CompositeDisposable()
    private val liveData = MutableLiveData<PagedList<User>>()
    private val liveEmailSet = MutableLiveData<MutableSet<String?>>()

    private val userRepository = UserRepositoryImpl(UserService.getInstance()!!)
    private val databaseRepository =
        UserDatabaseRepositoryImpl(
            application.applicationContext
        )

    private val userDataSource = UserDataSource(userRepository)
    private val databaseDataSource = DatabaseDataSource(databaseRepository)

    init{
        getUsersFromNetwork()
        getEmailsFromDatabase()
    }

    fun getLiveData(): LiveData<PagedList<User>> = liveData
    fun getLiveEmailSet(): MutableLiveData<MutableSet<String?>> = liveEmailSet

    fun addUserToDatabase(user: User) {
            if(!databaseRepository.checkUserExists(user.id)){
                databaseRepository.addUserToDatabase(user)
                getEmailsFromDatabase()
            }
    }

    fun getUserFromDatabaseById(id: Int): User? {
        return databaseRepository.getUserFromDatabaseById(id)
    }

    fun getUsersFromDatabase() {
        val config = PagedList.Config.Builder()
            .setPageSize(100)
            .setEnablePlaceholders(false)
            .setPrefetchDistance(100)
            .build()

        val dataSource: Flowable<PagedList<User>> =
            RxPagedListBuilder(
                DatabaseSourceFactory(databaseDataSource),
                config
            ).buildFlowable(BackpressureStrategy.BUFFER)

        disposables.add(dataSource
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError = { t -> Log.d("database error", t.toString()) },
                onNext = { pagedList -> liveData.value = pagedList }
            ))
    }

    fun getEmailsFromDatabase() {
             disposables.add(
                 databaseRepository.getEmails()
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribeBy(
                     onSuccess = { liveEmailSet.value = it }
                 ))
    }

    fun clearDatabase(){
        databaseRepository.clearDatabase()
    }

    private fun getUsersFromNetwork() {
        val config = PagedList.Config.Builder()
            .setPageSize(6)
            .setEnablePlaceholders(false)
            .build()

        val dataSource: Flowable<PagedList<User>> =
            RxPagedListBuilder(
                UserDataSourceFactory(userDataSource),
                config
            ).buildFlowable(BackpressureStrategy.BUFFER)

        disposables.add(dataSource
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError = {t -> Log.d("network error", t.toString())},
                onNext = { pagedList -> liveData.value = pagedList}
            )
        )
    }

    fun postUser(user: PostUser) {
        disposables.add(
            userRepository.postUser(
                PostUser(
                    first_name = user.first_name,
                    last_name = user.last_name,
                    email = user.email
                )
            ).toFlowable()
                .throttleFirst(5000,TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeBy(
                onError = { t: Throwable? -> Log.d("post-response", t.toString()) },
                onNext = { u ->
                    Log.d(
                        "post-response",
                        "${u.email}, ${u.first_name} ${u.last_name}, ${u.id}"
                    )
                    addUserToDatabase(u)
                }
            )
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}