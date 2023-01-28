package br.com.dhungria.mobappsmovies.ui.homemovies

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
import br.com.dhungria.mobappsmovies.R
import br.com.dhungria.mobappsmovies.adapter.NowPlayingMoviesAdapter
import br.com.dhungria.mobappsmovies.adapter.PopularMoviesAdapter
import br.com.dhungria.mobappsmovies.data.models.MovieNowPlayingModel
import br.com.dhungria.mobappsmovies.databinding.HomeMoviesFragmentBinding
import br.com.dhungria.mobappsmovies.viewmodel.HomeMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeMoviesFragment : Fragment() {

    private lateinit var binding: HomeMoviesFragmentBinding

    private val popularMoviesAdapter = PopularMoviesAdapter(
        onClick = {
            findNavController().navigate(
                R.id.action_home_movies_to_detail_movie,
                bundleOf("movie_id_to_detail" to it.id)
            )
        }
    )

    private val viewModel: HomeMoviesViewModel by viewModels()

    private val nowPlayingMoviesAdapter = NowPlayingMoviesAdapter(
        onClick = {
            findNavController().navigate(
                R.id.action_home_movies_to_detail_movie,
                bundleOf("movie_id_to_detail" to it.id)
            )
        }
    )


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

    private fun setupSwipeRefresh() = with(binding){
        homeFragmentSwipeRefresh.setOnRefreshListener {
            lifecycleScope.launch {
                viewModel.getMoviesNowPlayingData()
                viewModel.getMoviesTopRatedData()
                homeFragmentSwipeRefresh.isRefreshing = false
            }
        }
    }


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
        setupSwipeRefresh()
        viewModel.getMoviesNowPlayingData()
        viewModel.getMoviesTopRatedData()
        viewModel.moviesNowPlayingModel.observe(viewLifecycleOwner) {
            nowPlayingMoviesAdapter.updateList(it.results)
            setupPageListener(it)
        }
        viewModel.moviesTopRatedModel.observe(viewLifecycleOwner){
            popularMoviesAdapter.updateList(it.results)
        }

    }
}
