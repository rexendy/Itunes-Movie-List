package com.test.movieapp.movie.view.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.test.domain.movie.model.Movie
import com.test.movieapp.databinding.MovieItemLayoutBinding
import com.test.movieapp.R

class MovieAdapter(): RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private lateinit var context: Context
    private var movies: MutableList<Movie> = mutableListOf()

    interface OnClickListener {
        fun onClick(item: Movie)
    }

    var onClickListener: OnClickListener? = null

    class ViewHolder(
        val binding: MovieItemLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root)

    fun setMovies(data: MutableList<Movie>) {
        movies = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: MovieItemLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.movie_item_layout,
            parent,
            false
        )
        context = parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = movies.getOrNull(position) ?: return
        val imageUrl = item.artworkUrl100
        val name = item.trackName
        val genre = item.primaryGenreName
        val price = item.trackPrice
        val currency = item.currency
        holder.binding.run {
            cardViewContainer.setOnClickListener{
                onClickListener?.onClick(item)
            }

            Glide.with(context)
                .load(imageUrl)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_no_image))
                .into(imgArtwork)

            txtTrackName.text = name
            txtGenre.text = genre
            txtPrice.text =
                if (price <= 0.0) context.getString(R.string.free) else "$currency $price"
        }

    }

    override fun getItemCount(): Int {
        return movies.size
    }
}