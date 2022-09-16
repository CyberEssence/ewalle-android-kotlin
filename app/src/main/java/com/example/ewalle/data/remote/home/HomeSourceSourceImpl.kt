package com.example.ewalle.data.remote.home

import com.example.ewalle.data.model.home.Services
import com.example.ewalle.data.model.home.Users
import io.reactivex.Single
import javax.inject.Inject

class HomeRemoteSourceImpl @Inject constructor() : HomeRemoteSource {

    companion object {
        private const val BALANCE = 20600
    }

    override fun getBalance(): Single<Int> = Single.just(BALANCE)

    override fun getListUsers(): Single<List<Users>> = Single.just(
        listOf(
            Users("Mike", ""),
            Users("Joshpeh", ""),
            Users("Dasha", "https://pbs.twimg.com/media/ETnk8TzUcAA3Ohj.jpg")
        )
    )

    override fun geListServices(): Single<List<Services>> = Single.just(
        listOf(
            Services("Send Money", ""),
            Services("Receive Money", ""),
            Services("Mobile Prepaid", ""),
            Services("Electricity", ""),
            Services("Cashback Offer", ""),
            Services("Movie Tickets", ""),
            Services("Flight Tickets", ""),
            Services("More Options", "")
        ))
}