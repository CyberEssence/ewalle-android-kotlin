package com.example.ewalle.data.cache.home

import com.example.ewalle.data.model.home.HomeData
import io.reactivex.Single
import javax.inject.Inject

class MemoryCacheHomeImpl @Inject constructor() : MemoryCacheHome {
    private var homeData: HomeData = HomeData(0, listOf(), listOf())

    override fun getHomeData() = Single.just(homeData)

    override fun setHomeData(homeData: HomeData) {
        this.homeData = homeData
    }
}