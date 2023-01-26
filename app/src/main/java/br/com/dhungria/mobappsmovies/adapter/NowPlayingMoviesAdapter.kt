package br.com.dhungria.mobappsmovies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.dhungria.mobappsmovies.databinding.CardViewRecyclerNowPlayingBinding

class NowPlayingMoviesAdapter(
    val onClick: () -> Unit
) :
    ListAdapter<String, NowPlayingMoviesAdapter.ViewHolder>(DiffCallback()) {

//    private var fullList = mutableListOf<Training>()

//    private fun showMenu(
//        context: Context,
//        view: View,
//        menuPopupMenu: Int,
//        training: Training
//    ) {
//        val popup = PopupMenu(context, view)
//        popup.menuInflater.inflate(menuPopupMenu, popup.menu)
//        popup.setOnMenuItemClickListener {
//            when (it.itemId) {
//                R.id.edit_popup_menu -> {
//                    onLongPressEdit(training)
//                    true
//                }
//                R.id.delete_popup_menu -> {
//                    onLongPressDelete(training)
//                    true
//                }
//                else -> false
//            }
//        }
//        popup.show()
//    }

//    fun updateList(listTraining: List<Training>) {
//        fullList = listTraining.toMutableList()
//
//        submitList(fullList)
//    }

    inner class ViewHolder(
        private val binding: CardViewRecyclerNowPlayingBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(currentItem: String) {
            binding.root.setOnClickListener {
                onClick()
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

    class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem
    }


}