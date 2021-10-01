package com.test.movieapp.movie.view.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.test.domain.movie.model.Movie
import com.test.domain.session.model.Session
import com.test.movieapp.R
import com.test.movieapp.core.injector
import com.test.movieapp.databinding.FragmentMoviesBinding
import com.test.movieapp.movie.event.GoToMovieDetail
import com.test.movieapp.movie.view_model.MovieViewModel
import java.util.*

class MovieFragment: Fragment() {
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieAdapter: MovieAdapter
    private val factory = injector.movieVMFactory()
    private var isFromOnResume = true

    private val binding: FragmentMoviesBinding by lazy {
        FragmentMoviesBinding.inflate(requireActivity().layoutInflater)
    }

    private val onClickListener = object: MovieAdapter.OnClickListener {
        override fun onClick(item: Movie) {
            GoToMovieDetail.post(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieViewModel = ViewModelProviders.of(requireNotNull(activity), factory).get(MovieViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        movieAdapter = MovieAdapter().apply {
            onClickListener = this@MovieFragment.onClickListener
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)  {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
    }

    private fun initBinding() {
        binding.progressBar.run {
            visibility = View.VISIBLE
        }

        movieViewModel.movies().observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = View.GONE
            movieAdapter.setMovies(it.toMutableList())
        })

        movieViewModel.errorMsg().observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.progressBar.visibility = View.GONE
                val snackBar = Snackbar.make(binding.rvMovies, it, Snackbar.LENGTH_LONG)
                snackBar.show()
            }
        })

        movieViewModel.sessionLiveData().observe(viewLifecycleOwner, Observer { session ->
            binding.txtLastVisited.text = if (session.dateLastVisited.isNullOrEmpty())  context?.getString(R.string.just_now) else session.dateLastVisited
            checkSessionData(session)
        })

        binding.rvMovies.run {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
            adapter = movieAdapter
        }

        movieViewModel.getMovies()
    }

    override fun onResume() {
        super.onResume()
        isFromOnResume = true
        movieViewModel.getDate()
        movieViewModel.getSession()
    }

    private fun checkSessionData(session: Session) {
        if (!isFromOnResume) return

        val session = session
        session.let {
            if (it == null) {
                saveSession("", movieViewModel.currentDate, 0)
            } else if (it.dateCurrentVisited != movieViewModel.currentDate) {
                saveSession(it.dateCurrentVisited, movieViewModel.currentDate, it.trackId)
            }
        }
        isFromOnResume = false
    }

    private fun saveSession(lastDate: String?, currentDate: String?, trackId: Long) {
        movieViewModel.saveSession(
            lastDate,
            currentDate,
            trackId
        )
    }
}