package com.example.ewalle.data.remote.login

import com.example.ewalle.data.model.login.Weather
import io.reactivex.Single

interface LoginRemoteSource {
    fun getWeather(): Single<Weather>
}