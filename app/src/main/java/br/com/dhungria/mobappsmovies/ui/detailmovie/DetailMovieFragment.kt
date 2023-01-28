package br.com.dhungria.mobappsmovies.ui.detailmovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.dhungria.mobappsmovies.R
import br.com.dhungria.mobappsmovies.adapter.GenresPlayingMoviesAdapter
import br.com.dhungria.mobappsmovies.constants.Constants.MOVIE_ID_TO_DETAIL
import br.com.dhungria.mobappsmovies.data.models.MovieDetailsModel
import br.com.dhungria.mobappsmovies.data.retrofit.URL_LOAD_IMAGE
import br.com.dhungria.mobappsmovies.databinding.DetailMoviesFragmentBinding
import br.com.dhungria.mobappsmovies.extensions.tryLoadImage
import br.com.dhungria.mobappsmovies.viewmodel.DetailMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailMovieFragment : Fragment() {

    private lateinit var binding: DetailMoviesFragmentBinding

    private val viewModel: DetailMoviesViewModel by viewModels()

    private val movieID by lazy { arguments?.getInt(MOVIE_ID_TO_DETAIL) }

    private val genresMoviesAdapter = GenresPlayingMoviesAdapter()


    private fun setupItemsView(movieDetailsModel: MovieDetailsModel?) = with(binding) {
        movieDetailsModel?.let {
            val runtimeMovie = movieDetailsModel.runtime.toLong()
            val hour = (runtimeMovie / 60).toString()
            val minutes = (runtimeMovie % 60).toString()

            textViewTitleDetailMovie.text = movieDetailsModel.title
            textViewDescriptionDetailMovie.text = movieDetailsModel.overview
            textViewRatedMovieDetail.text = getString(
                R.string.movie_detail_average_movie,
                movieDetailsModel.vote_average.toString()
            )
            textViewTimeDurationDetailMovie.text =
                getString(R.string.movie_detail_time_duration, hour, minutes)

            genresMoviesAdapter.updateList(it.genres)

            imageViewDetailMovieFragment
                .tryLoadImage(URL_LOAD_IMAGE + (movieDetailsModel.backdrop_path))
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

    private fun setupSwipeRefresh() = with(binding) {
        detailMovieFragmentSwipeRefresh.setOnRefreshListener {
            lifecycleScope.launch {
                movieID?.let { viewModel.getMoviesNowPlayingData(it) }
                detailMovieFragmentSwipeRefresh.isRefreshing = false
            }
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
        setupSwipeRefresh()
        binding.toolbarDetailMovieFragment.setNavigationOnClickListener { findNavController().popBackStack() }
        lifecycleScope.launch{ movieID?.let { viewModel.getMoviesNowPlayingData(it) } }
        viewModel.detailMovieModel.observe(viewLifecycleOwner) { setupItemsView(it) }
    }
}
