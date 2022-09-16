package com.example.ewalle.domain.repositories.login

import com.example.ewalle.data.model.login.DateTime
import com.example.ewalle.data.model.login.Weather
import com.example.ewalle.data.receiver.DateTimeReceiver
import com.example.ewalle.data.remote.login.LoginRemoteSource
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class LoginReposImpl @Inject constructor(
    private val loginRemoteSource: LoginRemoteSource,
    private val dateTimeReceiver: DateTimeReceiver
) : LoginRepos {
    override fun getDateTime(): Observable<DateTime> =
        dateTimeReceiver.getDateTime()

    override fun getWeather(): Single<Weather> =
        loginRemoteSource.getWeather()
}