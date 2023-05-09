package br.com.dhungria.mobappsmovies.repositories

import br.com.dhungria.mobappsmovies.data.retrofit.RetrofitService
import javax.inject.Inject

class Repository @Inject constructor(private val retrofitService: RetrofitService) {

    fun getMoviesNowPlaying(page: Int) = retrofitService.getMoviesNowPlaying(page)

    fun getDetailMovie(id: Int) = retrofitService.getDetailMovie(id)

    fun getTopRatedMovie() = retrofitService.getTopRatedMovie()

    fun searchMovie(queryMovie: String) = retrofitService.searchMovie(queryMovie)

    fun getMovieRecommendations(id: Int, page: Int) = retrofitService.getMovieRecommendations(id, page)

}