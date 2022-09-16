package com.example.ewalle.data.remote.menu

import com.example.ewalle.data.model.menu.User
import io.reactivex.Single

interface MenuRemoteSource {
    fun getUser(): Single<User>
}