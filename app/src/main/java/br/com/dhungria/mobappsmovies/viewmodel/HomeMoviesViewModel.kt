package br.com.dhungria.mobappsmovies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.dhungria.mobappsmovies.data.models.MovieNowPlayingModel
import br.com.dhungria.mobappsmovies.data.models.MovieTopRatedModel
import br.com.dhungria.mobappsmovies.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeMoviesViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _moviesNowPlayingModel = MutableLiveData<MovieNowPlayingModel>()
    val moviesNowPlayingModel: LiveData<MovieNowPlayingModel>
        get() = _moviesNowPlayingModel


    private val _moviesTopRatedModel = MutableLiveData<MovieTopRatedModel>()
    val moviesTopRatedModel: LiveData<MovieTopRatedModel>
        get() = _moviesTopRatedModel

    val errorMessage = MutableLiveData<String>()

    var page = 1


    fun getMoviesNowPlayingData() {
        val request = repository.getMoviesNowPlaying(page)

        request.enqueue(object : Callback<MovieNowPlayingModel> {
            override fun onResponse(
                call: Call<MovieNowPlayingModel>,
                response: Response<MovieNowPlayingModel>
            ) {
                _moviesNowPlayingModel.postValue(response.body())
            }
            override fun onFailure(call: Call<MovieNowPlayingModel>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    fun getMoviesTopRatedData() {
        val request = repository.getTopRatedMovie()

        request.enqueue(object : Callback<MovieTopRatedModel> {
            override fun onResponse(
                call: Call<MovieTopRatedModel>,
                response: Response<MovieTopRatedModel>
            ) {
                _moviesTopRatedModel.postValue(response.body())
            }
            override fun onFailure(call: Call<MovieTopRatedModel>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

}