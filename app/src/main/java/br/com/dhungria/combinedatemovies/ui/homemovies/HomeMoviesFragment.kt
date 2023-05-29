package br.com.dhungria.combinedatemovies.ui.homemovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.dhungria.combinedatemovies.R
import br.com.dhungria.combinedatemovies.adapter.NowPlayingMoviesAdapter
import br.com.dhungria.combinedatemovies.adapter.PopularMoviesAdapter
import br.com.dhungria.combinedatemovies.constants.Constants.MOVIE_ID_TO_DETAIL
import br.com.dhungria.combinedatemovies.data.models.MovieNowPlayingModel
import br.com.dhungria.combinedatemovies.databinding.HomeMoviesFragmentBinding
import br.com.dhungria.combinedatemovies.viewmodel.HomeMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeMoviesFragment : Fragment() {

    private lateinit var binding: HomeMoviesFragmentBinding
    private val viewModel: HomeMoviesViewModel by viewModels()
    private val popularMoviesAdapter = PopularMoviesAdapter(
        onClick = {
            findNavController().navigate(
                R.id.action_home_movies_to_detail_movie,
                bundleOf(MOVIE_ID_TO_DETAIL to it.id)
            )
        }
    )
    private val nowPlayingMoviesAdapter = NowPlayingMoviesAdapter(
        onClick = {
            findNavController().navigate(
                R.id.action_home_movies_to_detail_movie,
                bundleOf(MOVIE_ID_TO_DETAIL to it.id)
            )
        }
    )



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeMoviesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerPopularMovies()
        setupRecyclerNowPlayingMovies()
        lifecycleScope.launch {
            viewModel.getMoviesNowPlayingData()
            viewModel.getMoviesTopRatedData()
        }
        viewModel.moviesNowPlayingModel.observe(viewLifecycleOwner) {
            nowPlayingMoviesAdapter.updateList(it.results)
            setupPageListener(it)
        }
        viewModel.moviesTopRatedModel.observe(viewLifecycleOwner) { popularMoviesAdapter.updateList(it.results) }

        setupButtonCombineMovies()

    }

    private fun setupButtonCombineMovies() {
//        binding.buttonCombineMovies.setOnClickListener {
//            findNavController().navigate(R.id.action_home_movies_to_combine_movies)
//        }
    }



    private fun setupRecyclerPopularMovies() {
        binding.recyclerViewPopularMoviesHomeFragment.apply {
            adapter = popularMoviesAdapter
            val mLayoutManager = LinearLayoutManager(requireContext())
            mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = mLayoutManager
        }
    }

    private fun setupRecyclerNowPlayingMovies() {
        binding.recyclerViewNowPlayingMoviesHomeFragment.apply {
            adapter = nowPlayingMoviesAdapter
            val mLayoutManager = LinearLayoutManager(requireContext())
            mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = mLayoutManager
        }
    }

    private fun setupPageListener(it: MovieNowPlayingModel) = with(binding) {
        var page = viewModel.page
        textViewPageNumber.text = it.page.toString()
        buttonNavigateNextPage.setOnClickListener { _ ->
            if (page < it.total_pages) {
                page++
                viewModel.page = page
                viewModel.getMoviesNowPlayingData()
            }
        }
        buttonNavigatePreviousPage.setOnClickListener { _ ->
            if (page > 1) {
                page--
                viewModel.page = page
                viewModel.getMoviesNowPlayingData()
            }
        }
    }
}
