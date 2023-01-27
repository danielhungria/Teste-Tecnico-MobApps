package br.com.dhungria.mobappsmovies.ui.homemovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.dhungria.mobappsmovies.R
import br.com.dhungria.mobappsmovies.adapter.NowPlayingMoviesAdapter
import br.com.dhungria.mobappsmovies.adapter.PopularMoviesAdapter
import br.com.dhungria.mobappsmovies.data.models.MovieNowPlayingModel
import br.com.dhungria.mobappsmovies.databinding.HomeMoviesFragmentBinding
import br.com.dhungria.mobappsmovies.viewmodel.HomeMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeMoviesFragment : Fragment() {

    private lateinit var binding: HomeMoviesFragmentBinding

    private val popularMoviesAdapter = PopularMoviesAdapter()

    private val viewModel: HomeMoviesViewModel by viewModels()

    private var page = 1


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
            adapter = popularMoviesAdapter.apply {
                submitList(List(10, init = {
                    "test"
                })).toString()
            }
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
        viewModel.getMoviesNowPlayingData(page)
        viewModel.moviesNowPlayingModel.observe(viewLifecycleOwner) {
            nowPlayingMoviesAdapter.updateList(it.results)
            setupPageListenet(it)

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                binding.recyclerViewNowPlayingMoviesHomeFragment.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
//
//                    if (!v.canScrollHorizontally(1)) {
////                        if (page < it.total_pages) {
////                            page++
////                            viewModel.getMoviesNowPlayingData(page)
////                            nowPlayingMoviesAdapter.updateList(it.results)
////                        }
//
//                    } else if (!v.canScrollHorizontally(-1)) {
//                        if (page > 1) {
//                            page--
//                            viewModel.getMoviesNowPlayingData(page)
//                            nowPlayingMoviesAdapter.updateList(it.results)
//                        }
//                    }
//
//
//                }
//            }

        }

    }

    private fun setupPageListenet(it: MovieNowPlayingModel) = with(binding) {
        textViewPageNumber.text = it.page.toString()
        buttonNavigateNextPage.setOnClickListener { _ ->
            if (page < it.total_pages) {
                page++
                viewModel.getMoviesNowPlayingData(page)
            }
        }
        buttonNavigatePreviousPage.setOnClickListener { _ ->
            if (page > 1) {
                page--
                viewModel.getMoviesNowPlayingData(page)

            }
        }
    }

}
