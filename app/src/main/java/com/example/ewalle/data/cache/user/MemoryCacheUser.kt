package com.example.ewalle.data.cache.user

import com.example.ewalle.data.model.menu.User
import io.reactivex.Single

interface MemoryCacheUser {
    fun getUser(): Single<User>
    fun setUser(user: User)
}