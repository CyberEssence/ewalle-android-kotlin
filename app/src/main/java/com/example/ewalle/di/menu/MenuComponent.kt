package com.example.ewalle.di.menu

import com.example.ewalle.presentation.main.MainActivity
import dagger.Subcomponent
import javax.inject.Singleton

@Subcomponent(modules = [MenuViewModelModule::class])
@Singleton
interface MenuComponent {

    fun inject(mainActivity: MainActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create(): MenuComponent
    }
}