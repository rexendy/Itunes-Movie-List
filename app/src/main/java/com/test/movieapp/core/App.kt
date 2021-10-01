package com.test.movieapp.core

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        injector = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .viewModelModule(ViewModelModule())
            .apiModule(ApiModule())
            .sharedPrefModule(SharedPrefModule())
            .build()
    }
}

lateinit var injector: AppComponent