package com.test.movieapp.core

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val context: Context) {
    @Provides
    fun providesAppContext(): Context = context
}