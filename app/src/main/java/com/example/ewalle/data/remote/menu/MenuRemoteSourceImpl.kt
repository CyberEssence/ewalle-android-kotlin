package com.example.ewalle.data.remote.menu

import com.example.ewalle.data.model.menu.User
import io.reactivex.Single
import javax.inject.Inject

class MenuRemoteSourceImpl @Inject constructor() : MenuRemoteSource {
    override fun getUser(): Single<User> =
        Single.just(User("Carol", "", "Seattle,Washington"))
}