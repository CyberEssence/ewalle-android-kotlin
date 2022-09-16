package com.example.ewalle.domain.usecases.menu

import com.example.ewalle.domain.repositories.menu.MenuRepos
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val menuRepository: MenuRepos) {
    fun getUser() = menuRepository.getUser()
}