package com.example.ewalle.di.home

import com.example.ewalle.presentation.home.HomeFragment
import com.example.ewalle.di.scope.FragmentScope
import dagger.Subcomponent

@Subcomponent(modules = [HomeViewModelModule::class])
@FragmentScope
interface HomeComponent {

    fun inject(homeFragment: HomeFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): HomeComponent
    }
}