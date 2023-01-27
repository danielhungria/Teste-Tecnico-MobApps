package br.com.dhungria.mobappsmovies.data.retrofit

import br.com.dhungria.mobappsmovies.data.models.MovieDetailsModel
import br.com.dhungria.mobappsmovies.data.models.MovieNowPlayingModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

const val BASE_URL = "https://api.themoviedb.org/3/movie/"
const val KEY_API = "9a8c5b27ecbe0c0f832b960e70865d8d"
const val LANGUAGE = "pt-BR"
const val PAGE = 1


interface RetrofitService {

    @GET("now_playing?api_key=$KEY_API&language=$LANGUAGE")
    fun getMoviesNowPlaying(): Call<MovieNowPlayingModel>

    @GET("{ID_MOVIE}?api_key=$KEY_API&language=$LANGUAGE")
    fun getDetailMovie(
        @Path("ID_MOVIE") id: Int
    ): Call<MovieDetailsModel>

}