package com.test.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.test.domain.movie.model.Movie
import com.test.movieapp.core.injector
import com.test.movieapp.databinding.ActivityMainBinding
import com.test.movieapp.movie.event.GoToMovieDetail
import com.test.movieapp.movie.view.detail.MovieDetailFragment
import com.test.movieapp.movie.view.list.MovieFragment
import com.test.movieapp.movie.view_model.MovieViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var movieViewModel: MovieViewModel
    private val factory = injector.movieVMFactory()

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieViewModel = ViewModelProviders.of(this, factory).get(MovieViewModel::class.java)
        initBinding()
        movieViewModel.getMovies()
        obsevers()
    }

    private fun initBinding() {
        showMovieFragment()
        movieViewModel.movies().observe(this, Observer {
            // check session data
            movieViewModel.session?.trackId.let {
                if (it !=null && it > 0) {
                    movieViewModel.getMovieDetail(it)
                    val movieDetail = movieViewModel.movieDetail
                    movieDetail.let { detail ->
                        if (detail == null) return@let
                        goToMovieDetail(detail)
                    }
                }
            }
        })

        movieViewModel.errorMsg().observe(this, Observer {
            it?.let {
                val snackBar = Snackbar.make(binding.fragmentContainer, it, Snackbar.LENGTH_LONG)
                snackBar.show()
            }
        })
    }

    private fun showMovieFragment() {
        supportFragmentManager.beginTransaction().apply {
            replace(binding.fragmentContainer.id, MovieFragment())
            commit()
        }
    }

    private fun obsevers() {
        GoToMovieDetail.observe(this, Observer {
            it ?: return@Observer
            goToMovieDetail(it)
            GoToMovieDetail.post(null)
        })
    }

    private fun goToMovieDetail(movie: Movie) {
        // update session save track id
        val session = movieViewModel.session
        saveSession(session?.dateLastVisited, session?.dateCurrentVisited, movie.trackId)
        supportFragmentManager.beginTransaction().apply {
            addToBackStack(MovieDetailFragment.ID)
            add(binding.fragmentContainer.id, MovieDetailFragment.newInstance(
                imgUrl = movie.artworkUrl100,
                trackName = movie.trackName,
                genre = movie.primaryGenreName,
                price = movie.trackPrice,
                currency = movie.currency,
                description = movie.longDescription
            ))
            commit()
        }
    }

    private fun saveSession(dateVisited: String?, currentDate: String?, trackId: Long) {
        movieViewModel.saveSession(dateVisited, currentDate, trackId)
    }

    override fun onBackPressed() {
        when {
            supportFragmentManager.backStackEntryCount > 1 -> {
                supportFragmentManager.popBackStack()
                removeSessionTrackID()
            }
            supportFragmentManager.backStackEntryCount > 0 -> {
                removeSessionTrackID()
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    private fun removeSessionTrackID() {
        val session = movieViewModel.session
        // update session remove track id
        saveSession(session?.dateLastVisited, session?.dateCurrentVisited, 0)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportFragmentManager.popBackStack()
    }

    override fun onResume() {
        super.onResume()
        movieViewModel.getSession()
    }
}