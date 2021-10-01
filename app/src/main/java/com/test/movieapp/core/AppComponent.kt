package com.test.movieapp.core

import com.test.movieapp.movie.view_model.MovieViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        ApiModule::class,
        ViewModelModule::class,
        SharedPrefModule::class,
        AppModule::class
    ]
)

@Singleton
interface AppComponent {
    fun movieVMFactory(): MovieViewModelFactory
}