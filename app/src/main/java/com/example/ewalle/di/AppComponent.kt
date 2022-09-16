package com.example.ewalle.di

import android.content.Context
import com.example.ewalle.di.home.HomeComponent
import com.example.ewalle.di.login.LoginComponent
import com.example.ewalle.di.menu.MenuComponent
import dagger.BindsInstance
import dagger.Component

@Component(modules = [ViewModelFactoryModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun registerHomeComponent(): HomeComponent.Factory

    fun registerLoginComponent(): LoginComponent.Factory

    fun registerMenuComponent(): MenuComponent.Factory
}