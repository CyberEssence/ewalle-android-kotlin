package com.example.ewalle.data.model.home

data class HomeData(
    val balance: Int,
    val listUsers: List<Users>,
    val listServices: List<Services>
)