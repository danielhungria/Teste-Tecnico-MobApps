package br.com.dhungria.mobappsmovies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.dhungria.mobappsmovies.data.models.MovieDetailsModel
import br.com.dhungria.mobappsmovies.data.models.MovieNowPlayingModel
import br.com.dhungria.mobappsmovies.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailMoviesViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _detailMovieModel = MutableLiveData<MovieDetailsModel>()
    val detailMovieModel: LiveData<MovieDetailsModel>
        get() = _detailMovieModel

    val errorMessage = MutableLiveData<String>()


    fun getMoviesNowPlayingData(id: Int) {
        val request = repository.getDetailMovie(id)

        request.enqueue(object : Callback<MovieDetailsModel> {
            override fun onResponse(
                call: Call<MovieDetailsModel>,
                response: Response<MovieDetailsModel>
            ) {
                _detailMovieModel.postValue(response.body())
            }
            override fun onFailure(call: Call<MovieDetailsModel>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

}