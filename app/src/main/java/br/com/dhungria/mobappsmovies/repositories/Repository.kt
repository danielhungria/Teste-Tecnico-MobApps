package br.com.dhungria.mobappsmovies.repositories

import br.com.dhungria.mobappsmovies.data.retrofit.RetrofitService
import javax.inject.Inject

class Repository @Inject constructor(private val retrofitService: RetrofitService) {

    fun getMoviesNowPlaying() = retrofitService.getMoviesNowPlaying()

    fun getDetailMovie(id: Int) = retrofitService.getDetailMovie(id)

}