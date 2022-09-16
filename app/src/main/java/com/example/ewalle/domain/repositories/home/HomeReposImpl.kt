package com.example.ewalle.domain.repositories.home

import com.example.ewalle.data.cache.home.MemoryCacheHome
import com.example.ewalle.data.model.home.HomeData
import com.example.ewalle.data.remote.home.HomeRemoteSource
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeReposImpl @Inject constructor(
    private val memoryCacheHome: MemoryCacheHome,
    private val homeDataRemoteSource: HomeRemoteSource,
) :
    HomeRepos {
    companion object {
        private const val DELAY_TIME: Long = 2
    }

    override fun getHomeData(): Single<HomeData> {
        return memoryCacheHome.getHomeData().flatMap {
            if (it.listServices.isEmpty()) {
                val balance =
                    homeDataRemoteSource.getBalance().delay(DELAY_TIME, TimeUnit.SECONDS)
                val users = homeDataRemoteSource.getListUsers()
                val services = homeDataRemoteSource.geListServices()
                Single.zip(balance, users, services) { b, u, s ->
                    HomeData(b, u, s).apply {
                        memoryCacheHome.setHomeData(this)
                    }
                }
            } else {
                Single.just(it)
            }
        }
    }
}