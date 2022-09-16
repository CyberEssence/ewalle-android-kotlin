package com.example.ewalle.di

import androidx.lifecycle.ViewModelProvider
import com.example.ewalle.presentation.factory.DaggerViewModelFactory
import dagger.Binds
import dagger.Module

@Module
interface ViewModelFactoryModule {

    @Binds
    fun bindsViewModelFactory(daggerViewModelFactory: DaggerViewModelFactory): ViewModelProvider.Factory
}