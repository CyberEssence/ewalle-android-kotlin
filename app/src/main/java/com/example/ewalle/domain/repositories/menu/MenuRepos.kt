package com.example.ewalle.domain.repositories.menu

import com.example.ewalle.data.model.menu.User
import io.reactivex.Single

interface MenuRepos {
    fun getUser(): Single<User>
}