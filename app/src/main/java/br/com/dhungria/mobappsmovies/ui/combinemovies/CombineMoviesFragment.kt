package br.com.dhungria.mobappsmovies.ui.combinemovies

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.dhungria.mobappsmovies.R
import br.com.dhungria.mobappsmovies.adapter.CombineMoviesAdapter
import br.com.dhungria.mobappsmovies.adapter.SimilarMoviesAdapter
import br.com.dhungria.mobappsmovies.constants.Constants
import br.com.dhungria.mobappsmovies.data.models.MovieNowPlayingModel
import br.com.dhungria.mobappsmovies.data.models.Result
import br.com.dhungria.mobappsmovies.data.retrofit.URL_LOAD_IMAGE
import br.com.dhungria.mobappsmovies.databinding.CoupleChooseMoviesFragmentBinding
import br.com.dhungria.mobappsmovies.extensions.tryLoadImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CombineMoviesFragment : Fragment() {

    private lateinit var binding: CoupleChooseMoviesFragmentBinding
    private val viewModel: CombineMoviesViewModel by viewModels()
    private var image1Clicked = false
    private var image2Clicked = false
    private val combineMoviesAdapter = CombineMoviesAdapter(
        onClick = {
            onClickAdapter(it)
        }
    )
    private val similarMoviesAdapter = SimilarMoviesAdapter(
        onClick = {
            findNavController().navigate(
                R.id.action_combine_movies_to_detail_movie,
                bundleOf(Constants.MOVIE_ID_TO_DETAIL to it.id)
            )
        }
    )
    private val scrollLocation = IntArray(2)
    private val x = scrollLocation[0]
    private val y = scrollLocation[1]


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
        setupRecyclerSimilarMovies()
        viewModel.searchMovie.observe(viewLifecycleOwner) {
            combineMoviesAdapter.updateList(it.results)
        }
        viewModel.similarMovies.observe(viewLifecycleOwner) {
            similarMoviesAdapter.updateList(it)
        }
        viewModel.recommendedMovies1.observe(viewLifecycleOwner){
            setupPageListener(it)
        }
        binding.editTextChooseMovie1.visibility = View.GONE
        binding.editTextChooseMovie2.visibility = View.GONE
        chooseMovieToCombine(view)
        getTextChooseMovie(x, y)
        buttonCombineMovie()
        if (viewModel.movieID1 != 0 && viewModel.movieID2 != 0){
            viewModel.getSimilarMovies()
            binding.recyclerListMovieGenerated.visibility = View.VISIBLE
            binding.buttonNavigateNextPage.visibility = View.VISIBLE
            binding.buttonNavigatePreviousPage.visibility = View.VISIBLE
            binding.textViewPageNumber.visibility = View.VISIBLE
            binding.imageViewCard1.tryLoadImage(viewModel.movieImage1)
            binding.imageViewCard2.tryLoadImage(viewModel.movieImage2)
        }
    }

    private fun buttonCombineMovie() {
        binding.buttonGenerateMovie.setOnClickListener {
            viewModel.getSimilarMovies()
            binding.recyclerListMovieGenerated.visibility = View.VISIBLE
            binding.buttonNavigateNextPage.visibility = View.VISIBLE
            binding.buttonNavigatePreviousPage.visibility = View.VISIBLE
            binding.textViewPageNumber.visibility = View.VISIBLE
            val layoutManager =
                binding.recyclerListMovieGenerated.layoutManager as LinearLayoutManager
            layoutManager.scrollToPositionWithOffset(0, 0)
            binding.scrollViewChooseMovieFragment.smoothScrollTo(x, y + 600)
        }
    }

    private fun getTextChooseMovie(x: Int, y: Int) {
        binding.editTextChooseMovie1.addTextChangedListener {
            binding.recyclerListMovieSearch.visibility = View.VISIBLE
            binding.buttonGenerateMovie.visibility = View.GONE
            viewModel.movieChoose = it.toString()
            viewModel.viewModelScope.launch(Dispatchers.IO) {
                viewModel.getSearchMovie(viewModel.movieChoose)
            }
            moveRecyclerListSearchToInit()
            if (it?.isBlank() == true) {
                binding.recyclerListMovieSearch.visibility = View.GONE
            }
            viewModel.editText1IsActive = true
            viewModel.editText2IsActive = false
            binding.scrollViewChooseMovieFragment.smoothScrollTo(x, y + 600)
        }
        binding.editTextChooseMovie2.addTextChangedListener {
            binding.recyclerListMovieSearch.visibility = View.VISIBLE
            binding.buttonGenerateMovie.visibility = View.GONE
            viewModel.movieChoose2 = it.toString()
            viewModel.viewModelScope.launch(Dispatchers.IO) {
                viewModel.getSearchMovie(viewModel.movieChoose2)
            }
            moveRecyclerListSearchToInit()
            if (it?.isBlank() == true) {
                binding.recyclerListMovieSearch.visibility = View.GONE
            }
            viewModel.editText1IsActive = false
            viewModel.editText2IsActive = true
            binding.scrollViewChooseMovieFragment.smoothScrollTo(x, y + 600)
        }
    }

    private fun moveRecyclerListSearchToInit() {
        val layoutManager = binding.recyclerListMovieSearch.layoutManager as LinearLayoutManager
        layoutManager.scrollToPosition(0)
    }

    private fun moveRecyclerMovieGeneratedToInit() {
        val layoutManager = binding.recyclerListMovieGenerated.layoutManager as LinearLayoutManager
        layoutManager.scrollToPosition(0)
    }

    private fun chooseMovieToCombine(view: View) {
        binding.imageViewCard1.setOnClickListener {
            binding.editTextChooseMovie2.setText("")
            binding.editTextChooseMovie2.visibility = View.GONE
            image1Clicked = !image1Clicked
            if (image1Clicked) {
                binding.buttonGenerateMovie.visibility = View.GONE
                binding.editTextChooseMovie1.apply {
                    visibility = View.VISIBLE
                    requestFocus()
                    val imm =
                        requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.showSoftInput(
                        binding.editTextChooseMovie1,
                        InputMethodManager.SHOW_IMPLICIT
                    )
                }
            } else {
                binding.editTextChooseMovie1.apply {
                    setText("")
                    visibility = View.GONE
                    val imm =
                        requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
                binding.buttonGenerateMovie.visibility = View.VISIBLE
            }
        }
        binding.imageViewCard2.setOnClickListener {
            binding.editTextChooseMovie1.setText("")
            binding.editTextChooseMovie1.visibility = View.GONE
            image2Clicked = !image2Clicked
            if (image2Clicked) {
                binding.buttonGenerateMovie.visibility = View.GONE
                binding.editTextChooseMovie2.apply {
                    visibility = View.VISIBLE
                    requestFocus()
                    val imm =
                        requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.showSoftInput(
                        binding.editTextChooseMovie2,
                        InputMethodManager.SHOW_IMPLICIT
                    )
                }
            } else {
                binding.editTextChooseMovie2.apply {
                    setText("")
                    visibility = View.GONE
                    val imm =
                        requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
                binding.buttonGenerateMovie.visibility = View.VISIBLE
            }
        }
    }

    private fun setupRecyclerSearchMovies() {
        binding.recyclerListMovieSearch.apply {
            val mLayoutManager = LinearLayoutManager(requireContext())
            mLayoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = mLayoutManager
            adapter = combineMoviesAdapter
        }
    }

    private fun setupRecyclerSimilarMovies() {
        binding.recyclerListMovieGenerated.apply {
            val mLayoutManager = LinearLayoutManager(requireContext())
            mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = mLayoutManager
            adapter = similarMoviesAdapter
        }
    }

    private fun onClickAdapter(it: Result) {
        if (viewModel.editText1IsActive) {
            binding.imageViewCard1.tryLoadImage(URL_LOAD_IMAGE + (it.poster_path))
            viewModel.movieImage1 = URL_LOAD_IMAGE + it.poster_path
            binding.editTextChooseMovie1.setText("")
            binding.recyclerListMovieSearch.visibility = View.GONE
            viewModel.movieID1 = it.id
            binding.editTextChooseMovie1.visibility = View.GONE
            binding.buttonGenerateMovie.visibility = View.VISIBLE

        } else if (viewModel.editText2IsActive) {
            binding.imageViewCard2.tryLoadImage(URL_LOAD_IMAGE + (it.poster_path))
            viewModel.movieImage2 = URL_LOAD_IMAGE + it.poster_path
            binding.editTextChooseMovie2.setText("")
            binding.recyclerListMovieSearch.visibility = View.GONE
            viewModel.movieID2 = it.id
            binding.editTextChooseMovie2.visibility = View.GONE
            binding.buttonGenerateMovie.visibility = View.VISIBLE
        }
        val imm = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun setupPageListener(it: MovieNowPlayingModel) = with(binding) {
        var page = viewModel.page
        textViewPageNumber.text = it.page.toString()
        buttonNavigateNextPage.setOnClickListener { _ ->
            if (page < it.total_pages) {
                page++
                viewModel.page = page
                viewModel.getSimilarMovies()
                moveRecyclerMovieGeneratedToInit()
            }
        }
        buttonNavigatePreviousPage.setOnClickListener { _ ->
            if (page > 1) {
                page--
                viewModel.page = page
                viewModel.getSimilarMovies()
                moveRecyclerMovieGeneratedToInit()
            }
        }

    }
}
