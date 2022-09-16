package com.example.ewalle.domain.usecases.login

import com.example.ewalle.domain.repositories.login.LoginRepos
import javax.inject.Inject

class GetLoginUseCase @Inject constructor(private val loginRepository: LoginRepos) {
    fun getDateTime() = loginRepository.getDateTime()
    fun getWeather() = loginRepository.getWeather()
}