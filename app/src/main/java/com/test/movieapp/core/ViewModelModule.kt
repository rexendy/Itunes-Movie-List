package com.test.movieapp.core

import com.test.data.movie.repository.MovieRepositoryIml
import com.test.data.session.repository.SessionRepositoryImpl
import com.test.domain.movie.usecase.GetMoviesUseCase
import com.test.domain.session.usecase.GetSessionUseCase
import com.test.domain.session.usecase.SaveSessionUseCase
import com.test.movieapp.movie.view_model.MovieViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {
    @Provides
    fun providesMovieVMFactory(
        movieRepositoryIml: MovieRepositoryIml,
        sessionRepositoryImpl: SessionRepositoryImpl): MovieViewModelFactory {
        return MovieViewModelFactory(
            GetMoviesUseCase(movieRepositoryIml),
            SaveSessionUseCase(sessionRepositoryImpl),
            GetSessionUseCase(sessionRepositoryImpl)
        )
    }
}