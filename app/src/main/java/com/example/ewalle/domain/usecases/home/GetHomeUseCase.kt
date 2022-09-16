package com.example.ewalle.domain.usecases.home

import com.example.ewalle.domain.repositories.home.HomeRepos
import javax.inject.Inject

class GetHomeUseCase @Inject constructor(private val homeRepository: HomeRepos) {
    fun getHomeData() = homeRepository.getHomeData()
}