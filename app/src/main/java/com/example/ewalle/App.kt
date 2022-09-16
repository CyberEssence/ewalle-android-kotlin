package com.example.ewalle

import android.app.Application
import com.example.ewalle.di.AppComponent
import com.example.ewalle.di.DaggerAppComponent

class App : Application() {

    private var appComponent: AppComponent? = null

    fun getAppComponent(): AppComponent {
        return appComponent ?: DaggerAppComponent.factory().create(applicationContext).also {
            appComponent = it
        }
    }
}