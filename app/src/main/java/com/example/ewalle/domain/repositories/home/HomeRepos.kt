package com.example.ewalle.domain.repositories.home

import com.example.ewalle.data.model.home.HomeData
import io.reactivex.Single

interface HomeRepos {
    fun getHomeData(): Single<HomeData>
}