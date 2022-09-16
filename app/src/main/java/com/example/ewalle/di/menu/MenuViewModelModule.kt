package com.example.ewalle.di.menu

import androidx.lifecycle.ViewModel
import com.example.ewalle.data.cache.user.MemoryCacheUser
import com.example.ewalle.data.cache.user.MemoryCacheUserImpl
import com.example.ewalle.data.remote.menu.MenuRemoteSource
import com.example.ewalle.data.remote.menu.MenuRemoteSourceImpl
import com.example.ewalle.di.keys.ViewModelKey
import com.example.ewalle.domain.repositories.menu.MenuRepos
import com.example.ewalle.domain.repositories.menu.MenuReposImpl
import com.example.ewalle.presentation.main.MenuViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MenuViewModelModule {

    @ViewModelKey(MenuViewModel::class)
    @IntoMap
    @Binds
    abstract fun bindsMenuViewModule(menuViewModel: MenuViewModel): ViewModel

    @Binds
    abstract fun bindsMenuRepository(menuRepositoryImpl: MenuReposImpl): MenuRepos

    @Binds
    abstract fun bindsMenuRemoteSource(menuRemoteSourceImpl: MenuRemoteSourceImpl): MenuRemoteSource

    @Binds
    abstract fun bindsMemoryCacheUser(memoryCacheUserImpl: MemoryCacheUserImpl): MemoryCacheUser
}