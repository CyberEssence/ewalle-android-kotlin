package com.example.ewalle.domain.repositories.login

import com.example.ewalle.data.model.login.DateTime
import com.example.ewalle.data.model.login.Weather
import io.reactivex.Observable
import io.reactivex.Single

interface LoginRepos {
    fun getDateTime(): Observable<DateTime>
    fun getWeather(): Single<Weather>
}