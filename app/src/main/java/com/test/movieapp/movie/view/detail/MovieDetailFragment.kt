package com.test.movieapp.movie.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.test.movieapp.R
import com.test.movieapp.databinding.FragmentMovieDetailBinding
import com.test.movieapp.extension.*

class MovieDetailFragment: Fragment() {

    companion object {
        const val ID = "MovieDetailFragment"
        fun newInstance(
            imgUrl: String?,
            trackName: String?,
            genre: String?,
            price: Double?,
            currency: String?,
            description: String?
        ): MovieDetailFragment {
            return MovieDetailFragment().apply {
                arguments = bundleOf(
                    "imgUrl" to imgUrl,
                    "trackName" to trackName,
                    "genre" to genre,
                    "price" to price,
                    "currency" to currency,
                    "description" to description
                )
            }
        }
    }

    private val binding: FragmentMovieDetailBinding by lazy {
        FragmentMovieDetailBinding.inflate(requireActivity().layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupBackButton(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)  {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
    }

    private fun setupBackButton(isShow: Boolean) {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(isShow)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                setupBackButton(false)
                activity?.onBackPressed()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun initBinding() {
        binding.imgArtwork.run {
            Glide.with(context)
                .load(arguments?.getImageUrl())
                .apply(RequestOptions.placeholderOf(R.drawable.ic_no_image))
                .into(this)
        }

        binding.txtPrice.run {
            text = "${arguments?.getCurrency()} ${arguments?.getPrice()}"
        }

        binding.txtGenre.run {
            text = arguments?.getGenre()
        }

        binding.txtDescription.run {
            text = arguments?.getDescription()
        }
    }
}