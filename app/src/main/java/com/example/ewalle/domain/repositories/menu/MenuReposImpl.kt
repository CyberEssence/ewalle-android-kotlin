package com.example.ewalle.domain.repositories.menu

import com.example.ewalle.data.cache.user.MemoryCacheUser
import com.example.ewalle.data.model.menu.User
import com.example.ewalle.data.remote.menu.MenuRemoteSource
import io.reactivex.Single
import javax.inject.Inject

class MenuReposImpl @Inject constructor(
    private val menuRemoteSource: MenuRemoteSource,
    private val memoryCacheUser: MemoryCacheUser
) : MenuRepos {
    override fun getUser(): Single<User> {
        return memoryCacheUser.getUser().flatMap {
            if (it.name == "") {
                menuRemoteSource.getUser().map {
                    it.apply { memoryCacheUser.setUser(it) }
                }
            } else {
                Single.just(it)
            }
        }
    }
}