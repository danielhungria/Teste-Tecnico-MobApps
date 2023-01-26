package br.com.dhungria.mobappsmovies.ui.homemovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.dhungria.mobappsmovies.R
import br.com.dhungria.mobappsmovies.adapter.NowPlayingMoviesAdapter
import br.com.dhungria.mobappsmovies.adapter.PopularMoviesAdapter
import br.com.dhungria.mobappsmovies.databinding.HomeMoviesFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeMoviesFragment : Fragment() {

    private lateinit var binding: HomeMoviesFragmentBinding

    private val popularMoviesAdapter = PopularMoviesAdapter()

    private val nowPlayingMoviesAdapter = NowPlayingMoviesAdapter(
        onClick = {
            findNavController().navigate(R.id.action_home_movies_to_detail_movie)
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
    }

    private fun setupRecyclerPopularMovies() {
        binding.recyclerViewPopularMoviesHomeFragment.apply {
            adapter = popularMoviesAdapter.apply {
                submitList(  List(10, init = {
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
            adapter = nowPlayingMoviesAdapter.apply {
                submitList(  List(10, init = {
                    "test"
                })).toString()
            }
            val mLayoutManager = LinearLayoutManager(requireContext())
            mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = mLayoutManager
        }
    }

}