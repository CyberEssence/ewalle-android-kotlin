package com.example.ewalle.data.remote.home

import com.example.ewalle.data.model.home.Services
import com.example.ewalle.data.model.home.Users
import io.reactivex.Single

interface HomeRemoteSource {
    fun getBalance(): Single<Int>
    fun getListUsers(): Single<List<Users>>
    fun geListServices(): Single<List<Services>>
}