package br.com.dhungria.combinedatemovies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.dhungria.combinedatemovies.data.models.Genre
import br.com.dhungria.combinedatemovies.databinding.CardViewRecyclerGenresDetailBinding

class GenresPlayingMoviesAdapter() :
    ListAdapter<Genre, GenresPlayingMoviesAdapter.ViewHolder>(DiffCallback()) {

    private var fullList = mutableListOf<Genre>()

    fun updateList(listMoviesNowPlaying: List<Genre>) {
        fullList = listMoviesNowPlaying.toMutableList()
        submitList(fullList)
    }

    inner class ViewHolder(
        private val binding: CardViewRecyclerGenresDetailBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: Genre) = with(binding) {
            textViewGenreCardMovieDetail.text = currentItem.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardViewRecyclerGenresDetailBinding.inflate(
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

    class DiffCallback : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre) =
            oldItem == newItem
    }
}