package br.com.dhungria.mobappsmovies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.dhungria.mobappsmovies.R
import br.com.dhungria.mobappsmovies.constants.Constants.DATE_PATTERN_DEFAULT
import br.com.dhungria.mobappsmovies.constants.Constants.DATE_PATTERN_Y_M_D
import br.com.dhungria.mobappsmovies.data.models.Result
import br.com.dhungria.mobappsmovies.data.retrofit.URL_LOAD_IMAGE
import br.com.dhungria.mobappsmovies.databinding.CardViewRecyclerNowPlayingBinding
import br.com.dhungria.mobappsmovies.extensions.tryLoadImage
import java.text.SimpleDateFormat
import java.util.*

class NowPlayingMoviesAdapter(
    val onClick: (Result) -> Unit
) :
    ListAdapter<Result, NowPlayingMoviesAdapter.ViewHolder>(DiffCallback()) {

    private var fullList = mutableListOf<Result>()

    fun updateList(listMoviesNowPlaying: List<Result>) {
        fullList = listMoviesNowPlaying.toMutableList()
        submitList(fullList)
    }

    inner class ViewHolder(
        private val binding: CardViewRecyclerNowPlayingBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: Result) = with(binding) {
            val dateDefault = currentItem.release_date
            val parser = SimpleDateFormat(DATE_PATTERN_Y_M_D, Locale.getDefault())
            val formatter = SimpleDateFormat(DATE_PATTERN_DEFAULT, Locale.getDefault())
            val result = parser.parse(dateDefault)?.let { formatter.format(it) }

            textViewTitleMovieCardNowPlaying.text = currentItem.title
            textViewAverageMovieCardNowPlaying.text = root.context.getString(
                R.string.text_average_movie,
                currentItem.vote_average.toString()
            )
            binding.textViewDateMovieCardNowPlaying.text = result
            imageViewCardViewNowPlaying.tryLoadImage(URL_LOAD_IMAGE + (currentItem.poster_path))

            root.setOnClickListener {
                onClick(currentItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardViewRecyclerNowPlayingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        return holder.bind(currentItem)
    }

    class DiffCallback : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Result, newItem: Result) =
            oldItem == newItem
    }
}