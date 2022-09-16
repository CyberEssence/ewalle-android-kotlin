package com.example.ewalle.data.remote.login

import com.example.ewalle.data.model.login.Weather
import io.reactivex.Single
import javax.inject.Inject

class LoginRemoteSourceImpl @Inject constructor() : LoginRemoteSource {
    override fun getWeather() = Single.just(Weather("34° C", ""))
}