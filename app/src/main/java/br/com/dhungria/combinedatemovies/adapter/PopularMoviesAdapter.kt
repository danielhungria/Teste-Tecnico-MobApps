package br.com.dhungria.combinedatemovies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.dhungria.combinedatemovies.data.models.ResultTopRated
import br.com.dhungria.combinedatemovies.data.retrofit.URL_LOAD_IMAGE
import br.com.dhungria.combinedatemovies.databinding.LayoutRecyclerPopularMoviesBinding
import br.com.dhungria.combinedatemovies.extensions.tryLoadImage

class PopularMoviesAdapter(
    val onClick: (ResultTopRated) -> Unit
) :
    ListAdapter<ResultTopRated, PopularMoviesAdapter.ViewHolder>(DiffCallback()) {

    private var fullList = mutableListOf<ResultTopRated>()


    fun updateList(listMovieTopRated: List<ResultTopRated>) {
        fullList = listMovieTopRated.toMutableList()
        submitList(fullList)
    }

    inner class ViewHolder(
        private val binding: LayoutRecyclerPopularMoviesBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: ResultTopRated) = with(binding) {
            imageCardView.tryLoadImage(URL_LOAD_IMAGE + (currentItem.poster_path))
            root.setOnClickListener {
                onClick(currentItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutRecyclerPopularMoviesBinding.inflate(
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

    class DiffCallback : DiffUtil.ItemCallback<ResultTopRated>() {
        override fun areItemsTheSame(oldItem: ResultTopRated, newItem: ResultTopRated) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ResultTopRated, newItem: ResultTopRated) =
            oldItem == newItem
    }
}