package com.example.ewalle.di.login

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.ViewModel
import com.example.ewalle.data.receiver.DateTimeReceiver
import com.example.ewalle.data.receiver.DateTimeReceiverImpl
import com.example.ewalle.data.remote.login.LoginRemoteSource
import com.example.ewalle.data.remote.login.LoginRemoteSourceImpl
import com.example.ewalle.di.keys.ViewModelKey
import com.example.ewalle.domain.repositories.login.LoginRepos
import com.example.ewalle.domain.repositories.login.LoginReposImpl
import com.example.ewalle.presentation.login.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.multibindings.IntoMap

@Module
abstract class LoginViewModelModule {

    @ViewModelKey(LoginViewModel::class)
    @IntoMap
    @Binds
    abstract fun bindsLoginViewModule(loginViewModel: LoginViewModel): ViewModel

    @Binds
    abstract fun bindsLoginRepository(loginRepositoryImpl: LoginReposImpl): LoginRepos

    @Binds
    abstract fun bindsLoginRemoteSource(loginRemoteSourceImpl: LoginRemoteSourceImpl): LoginRemoteSource

    companion object {
        @Reusable
        @Provides
        fun provideReceiver(context: Context): DateTimeReceiver {
            val intentFilter = IntentFilter(Intent.ACTION_TIME_TICK)
            return DateTimeReceiverImpl(context, intentFilter)
        }
    }
}