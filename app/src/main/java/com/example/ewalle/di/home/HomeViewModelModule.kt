package com.example.ewalle.di.home

import androidx.lifecycle.ViewModel
import com.example.ewalle.data.cache.home.MemoryCacheHome
import com.example.ewalle.data.cache.home.MemoryCacheHomeImpl
import com.example.ewalle.data.remote.home.HomeRemoteSource
import com.example.ewalle.data.remote.home.HomeRemoteSourceImpl
import com.example.ewalle.di.keys.ViewModelKey
import com.example.ewalle.domain.repositories.home.HomeRepos
import com.example.ewalle.domain.repositories.home.HomeReposImpl
import com.example.ewalle.presentation.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class HomeViewModelModule {

    @ViewModelKey(HomeViewModel::class)
    @IntoMap
    @Binds
    abstract fun bindsHomeViewModule(homeViewModel: HomeViewModel): ViewModel

    @Binds
    abstract fun bindsMemoryCacheHome(memoryCacheHomeImpl: MemoryCacheHomeImpl): MemoryCacheHome

    @Binds
    abstract fun bindsHomeRepository(homeRepositoryImpl: HomeReposImpl): HomeRepos

    @Binds
    abstract fun bindsPageRemoteSource(homeRemoteSourceImpl: HomeRemoteSourceImpl): HomeRemoteSource
}