package br.com.dhungria.mobappsmovies.ui.detailmovie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.dhungria.mobappsmovies.R
import br.com.dhungria.mobappsmovies.adapter.GenresPlayingMoviesAdapter
import br.com.dhungria.mobappsmovies.data.models.MovieDetailsModel
import br.com.dhungria.mobappsmovies.databinding.DetailMoviesFragmentBinding
import br.com.dhungria.mobappsmovies.extensions.tryLoadImage
import br.com.dhungria.mobappsmovies.viewmodel.DetailMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieFragment : Fragment() {

    private lateinit var binding: DetailMoviesFragmentBinding

    private val viewModel: DetailMoviesViewModel by viewModels()

    private val movieID by lazy { arguments?.getInt("movie_id_to_detail") }

    private val genresMoviesAdapter = GenresPlayingMoviesAdapter()


    private fun setupItemsView(movieDetailsModel: MovieDetailsModel?) = with(binding) {
        movieDetailsModel?.let {
            val runtimeMovie = movieDetailsModel.runtime.toLong()
            val hour = runtimeMovie / 60
            val minutes = runtimeMovie % 60

            textViewTitleDetailMovie.text = movieDetailsModel.title
            textViewDescriptionDetailMovie.text = movieDetailsModel.overview
            textViewRatedMovieDetail.text = "${movieDetailsModel?.vote_average}/10"
            textViewTimeDurationDetailMovie.text = "${hour}h ${minutes}min"

            genresMoviesAdapter.updateList(it.genres)

            imageViewDetailMovieFragment
                .tryLoadImage("https://image.tmdb.org/t/p/original${movieDetailsModel.backdrop_path}")
        }
    }

    private fun setupRecycler() {
        binding.recyclerViewGenresMovie.apply {
            adapter = genresMoviesAdapter
            val mLayoutManager = LinearLayoutManager(requireContext())
            mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = mLayoutManager
        }
    }

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
        setupRecycler()
        binding.toolbarDetailMovieFragment.setNavigationOnClickListener { findNavController().popBackStack() }
        binding.bookmarkDetailMovie.setOnClickListener { it.setBackgroundResource(R.drawable.ic_baseline_bookmark_24) }
        movieID?.let { viewModel.getMoviesNowPlayingData(it) }
        viewModel.detailMovieModel.observe(viewLifecycleOwner) { setupItemsView(it) }
    }


}
