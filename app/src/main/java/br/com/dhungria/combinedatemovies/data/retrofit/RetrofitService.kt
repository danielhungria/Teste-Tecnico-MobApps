package br.com.dhungria.combinedatemovies.data.retrofit

import br.com.dhungria.combinedatemovies.data.models.MovieDetailsModel
import br.com.dhungria.combinedatemovies.data.models.MovieNowPlayingModel
import br.com.dhungria.combinedatemovies.data.models.MovieTopRatedModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL = "https://api.themoviedb.org/3/"
const val URL_LOAD_IMAGE = "https://image.tmdb.org/t/p/original"
const val KEY_API = "9a8c5b27ecbe0c0f832b960e70865d8d"
const val LANGUAGE = "pt-BR"


interface RetrofitService {

    @GET("movie/now_playing?api_key=$KEY_API&language=$LANGUAGE")
    fun getMoviesNowPlaying(
        @Query("page") page: Int
    ): Call<MovieNowPlayingModel>

    @GET("movie/{ID_MOVIE}?api_key=$KEY_API&language=$LANGUAGE")
    fun getDetailMovie(
        @Path("ID_MOVIE") id: Int
    ): Call<MovieDetailsModel>

    @GET("movie/popular?api_key=$KEY_API&language=$LANGUAGE")
    fun getTopRatedMovie(): Call<MovieTopRatedModel>

    @GET("search/movie?api_key=$KEY_API&language=$LANGUAGE")
    fun searchMovie(
        @Query("query") queryMovie: String
    ): Call<MovieNowPlayingModel>

    @GET("movie/{ID_MOVIE}/recommendations?api_key=$KEY_API&language=$LANGUAGE")
    fun getMovieRecommendations(
        @Path("ID_MOVIE") id: Int,
        @Query("page") page: Int
    ): Call<MovieNowPlayingModel>


}