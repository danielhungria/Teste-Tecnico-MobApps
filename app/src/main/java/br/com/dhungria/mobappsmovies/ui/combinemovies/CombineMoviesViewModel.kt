package br.com.dhungria.mobappsmovies.ui.combinemovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.dhungria.mobappsmovies.data.models.MovieNowPlayingModel
import br.com.dhungria.mobappsmovies.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CombineMoviesViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _searchMovie = MutableLiveData<MovieNowPlayingModel>()
    val searchMovie: LiveData<MovieNowPlayingModel>
        get() = _searchMovie

    val errorMessage = MutableLiveData<String>()


    fun getSearchMovie(queryMovie: String) {
        val request = repository.searchMovie(queryMovie)

        request.enqueue(object : Callback<MovieNowPlayingModel> {
            override fun onResponse(
                call: Call<MovieNowPlayingModel>,
                response: Response<MovieNowPlayingModel>
            ) {
                _searchMovie.postValue(response.body())
            }

            override fun onFailure(call: Call<MovieNowPlayingModel>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

}