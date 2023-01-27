package br.com.dhungria.mobappsmovies.ui.detailmovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.dhungria.mobappsmovies.R
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

        movieID?.let { viewModel.getMoviesNowPlayingData(it) }

        viewModel.detailMovieModel.observe(viewLifecycleOwner){
            setupItemsView(it)
        }

    }

    private fun setupItemsView(movieDetailsModel: MovieDetailsModel?) = with(binding){

        textViewTitleDetailMovie.text = movieDetailsModel?.title
        textViewDescriptionDetailMovie.text = movieDetailsModel?.overview
        textViewRatedMovieDetail.text = "${movieDetailsModel?.vote_average}/10"


        when(movieDetailsModel?.genres?.size){
//
//            1 -> {
//                textViewGenreCard1MovieDetail.text = movieDetailsModel.genres[0].name
//                cardView2GenreMovieDetail.visibility = View.INVISIBLE
//                cardView3GenreMovieDetail.visibility = View.INVISIBLE
//            }
//            2 -> {
//                textViewGenreCard1MovieDetail.text = movieDetailsModel.genres[0].name
//                textViewGenreCard2MovieDetail.text = movieDetailsModel.genres[1].name
//                cardView3GenreMovieDetail.visibility = View.INVISIBLE
//            }
//            else -> {
//                if (movieDetailsModel != null) {
//                    textViewGenreCard1MovieDetail.text = movieDetailsModel.genres[0].name
//                    textViewGenreCard2MovieDetail.text = movieDetailsModel.genres[1].name
//                    textViewGenreCard3MovieDetail.text = movieDetailsModel.genres[2].name
//                }
//            }
//

        }

        imageViewDetailMovieFragment
            .tryLoadImage("https://image.tmdb.org/t/p/original${movieDetailsModel?.backdrop_path}")

    }

}