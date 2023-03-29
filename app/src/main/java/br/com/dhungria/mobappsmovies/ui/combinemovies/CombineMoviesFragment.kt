package br.com.dhungria.mobappsmovies.ui.combinemovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.dhungria.mobappsmovies.R
import br.com.dhungria.mobappsmovies.adapter.CombineMoviesAdapter
import br.com.dhungria.mobappsmovies.constants.Constants.MOVIE_ID_TO_DETAIL
import br.com.dhungria.mobappsmovies.data.retrofit.URL_LOAD_IMAGE
import br.com.dhungria.mobappsmovies.databinding.CoupleChooseMoviesFragmentBinding
import br.com.dhungria.mobappsmovies.extensions.tryLoadImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CombineMoviesFragment : Fragment() {

    private lateinit var binding: CoupleChooseMoviesFragmentBinding
    private val viewModel: CombineMoviesViewModel by viewModels()
    private val combineMoviesAdapter = CombineMoviesAdapter(
        onClick = {
            binding.imageViewCard1.tryLoadImage(URL_LOAD_IMAGE + (it.poster_path))
            binding.editTextChooseMovie1.setText(it.title)
        }
    )


    private fun setupRecyclerSearchMovies() {
        binding.recyclerListMovieSearch.apply {
            adapter = combineMoviesAdapter
            val mLayoutManager = LinearLayoutManager(requireContext())
            mLayoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = mLayoutManager
        }
    }
//
//    private fun setupSwipeRefresh() = with(binding) {
//        homeFragmentSwipeRefresh.setOnRefreshListener {
//            lifecycleScope.launch {
//                viewModel.getMoviesNowPlayingData()
//                viewModel.getMoviesTopRatedData()
//                homeFragmentSwipeRefresh.isRefreshing = false
//            }
//        }
//    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CoupleChooseMoviesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerSearchMovies()
        lifecycleScope.launch {
            viewModel.getSearchMovie("avatar")
        }
        viewModel.searchMovie.observe(viewLifecycleOwner) {
            combineMoviesAdapter.updateList(it.results)
        }
        binding.editTextChooseMovie2.addTextChangedListener {

        }
    }
}
