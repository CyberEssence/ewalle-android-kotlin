package com.example.ewalle.di.login

import com.example.ewalle.di.scope.FragmentScope
import com.example.ewalle.presentation.login.LoginFragment
import dagger.Subcomponent

@Subcomponent(modules = [LoginViewModelModule::class])
@FragmentScope
interface LoginComponent {

    fun inject(loginFragment: LoginFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginComponent
    }
}