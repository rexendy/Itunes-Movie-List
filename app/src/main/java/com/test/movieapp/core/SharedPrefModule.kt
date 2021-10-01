package com.test.movieapp.core

import android.content.Context
import android.content.SharedPreferences
import com.test.movieapp.R
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class SharedPrefModule {
    @Provides
    @Singleton
    @Named("MOVIE_SHARED_PREF")
    fun providesMovieSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(context.getString(R.string.movie_shared_pref), Context.MODE_PRIVATE)
    }
}