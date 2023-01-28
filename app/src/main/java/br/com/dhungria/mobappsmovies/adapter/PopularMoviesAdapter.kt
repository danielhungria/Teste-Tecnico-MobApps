package br.com.dhungria.mobappsmovies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.dhungria.mobappsmovies.data.models.Result_Top_Rated
import br.com.dhungria.mobappsmovies.data.retrofit.URL_LOAD_IMAGE
import br.com.dhungria.mobappsmovies.databinding.LayoutRecyclerPopularMoviesBinding
import br.com.dhungria.mobappsmovies.extensions.tryLoadImage

class PopularMoviesAdapter(
    val onClick: (Result_Top_Rated) -> Unit
) :
    ListAdapter<Result_Top_Rated, PopularMoviesAdapter.ViewHolder>(DiffCallback()) {

    private var fullList = mutableListOf<Result_Top_Rated>()


    fun updateList(listMovieTopRated: List<Result_Top_Rated>) {
        fullList = listMovieTopRated.toMutableList()
        submitList(fullList)
    }

    inner class ViewHolder(
        private val binding: LayoutRecyclerPopularMoviesBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: Result_Top_Rated) = with(binding) {
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

    class DiffCallback : DiffUtil.ItemCallback<Result_Top_Rated>() {
        override fun areItemsTheSame(oldItem: Result_Top_Rated, newItem: Result_Top_Rated) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Result_Top_Rated, newItem: Result_Top_Rated) =
            oldItem == newItem
    }
}