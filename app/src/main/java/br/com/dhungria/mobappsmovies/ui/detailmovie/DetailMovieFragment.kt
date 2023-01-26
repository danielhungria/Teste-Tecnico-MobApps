package br.com.dhungria.mobappsmovies.ui.detailmovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.dhungria.mobappsmovies.R
import br.com.dhungria.mobappsmovies.databinding.DetailMoviesFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieFragment : Fragment() {

    private lateinit var binding: DetailMoviesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailMoviesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarDetailMovieFragment.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.bookmarkDetailMovie.setOnClickListener {
            it.setBackgroundResource(R.drawable.ic_baseline_bookmark_24)
        }
    }

}