package com.test.movieapp.movie.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.domain.movie.model.Movie
import com.test.domain.movie.model.MovieRequestParam
import com.test.domain.movie.usecase.GetMoviesResult
import com.test.domain.movie.usecase.GetMoviesUseCase
import com.test.domain.session.model.Session
import com.test.domain.session.usecase.GetSessionUseCase
import com.test.domain.session.usecase.SaveSessionUseCase
import com.test.movieapp.utils.DateHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieViewModel(
    private val getMovies: GetMoviesUseCase,
    private val saveSession: SaveSessionUseCase,
    private val getSession: GetSessionUseCase
): ViewModel() {
    private val disposables = CompositeDisposable()
    private val movies = MutableLiveData<List<Movie>>()
    private val errorMsg = MutableLiveData<String>()
    private val param = MovieRequestParam(term = "star", country = "au", media = "movie")
    private val sessionLiveData = MutableLiveData<Session>()

    var session: Session? = null
    lateinit var currentDate: String
    var movieDetail: Movie? = null

    fun getMovies() {
        disposables.add(
            getMovies.execute(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        when(it) {
                            is GetMoviesResult.OnSuccess -> {
                                movies.value = it.movies
                            }
                            is GetMoviesResult.UnknownError -> {
                                errorMsg.value = "Unable to load movie list"
                            }
                        }
                    },
                    {
                        errorMsg.value = it.localizedMessage
                    }
                )
        )
    }

    fun getMovieDetail(id: Long) {
        movieDetail = movies.value?.filter { it.trackId == id }?.firstOrNull()
    }

    fun getSession() {
        disposables.add(
            getSession.execute()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        sessionLiveData.value = it
                        session = it
                    },
                    {
                        errorMsg.value = it.localizedMessage
                    }
                )
        )
    }

    fun saveSession(lastDate: String?, currentDate: String?, trackId: Long) {
        disposables.add(
            getSession.execute()
                .flatMapCompletable { session ->
                    saveSession.execute(
                        Session(
                            lastDate,
                            currentDate,
                            trackId = trackId
                        )
                    )
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { getSession() },
                    {
                        errorMsg.value = it.localizedMessage
                    }
                )
        )
    }

    fun getDate() {
        currentDate = DateHelper.returnCurrentDate()
    }

    fun errorMsg(): LiveData<String> = errorMsg
    fun movies(): LiveData<List<Movie>> = movies
    fun sessionLiveData(): LiveData<Session> = sessionLiveData

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}