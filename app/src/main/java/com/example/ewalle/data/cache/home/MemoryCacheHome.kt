package com.example.ewalle.data.cache.home

import com.example.ewalle.data.model.home.HomeData
import io.reactivex.Single

interface MemoryCacheHome {

    fun getHomeData(): Single<HomeData>
    fun setHomeData(homeData: HomeData)
}