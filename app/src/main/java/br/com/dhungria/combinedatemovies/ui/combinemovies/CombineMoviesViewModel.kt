package br.com.dhungria.combinedatemovies.ui.combinemovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dhungria.combinedatemovies.data.models.MovieNowPlayingModel
import br.com.dhungria.combinedatemovies.data.models.Result
import br.com.dhungria.combinedatemovies.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

@HiltViewModel
class CombineMoviesViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var editText2IsActive: Boolean = false
    var editText1IsActive: Boolean = false
     var movieChoose: String = ""
     var movieChoose2: String = ""

    var movieImage1: String = ""
    var movieImage2: String = ""

    var movieID1: Int = 0
    var movieID2: Int = 0

    var page = 1

    private val _searchMovie = MutableLiveData<MovieNowPlayingModel>()
    val searchMovie: LiveData<MovieNowPlayingModel>
        get() = _searchMovie

    private val _recommendedMovies1 = MutableLiveData<MovieNowPlayingModel>()
    val recommendedMovies1: LiveData<MovieNowPlayingModel>
        get() = _recommendedMovies1

    private val _similarMovies = MutableLiveData<List<Result>>()
    val similarMovies: LiveData<List<Result>>
        get() = _similarMovies

    val errorMessage = MutableLiveData<String>()


    fun getSearchMovie(queryMovie: String) {
        viewModelScope.launch(Dispatchers.IO) {
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
                    println(t.message)
                }
            })
        }

    }

    fun getSimilarMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val request1 = repository.getMovieRecommendations(movieID1, page)
            val request2 = repository.getMovieRecommendations(movieID2, page)

            var similarMovies1: List<Result>? = null
            var similarMovies2: List<Result>? = null

            val requestCountDownLatch = CountDownLatch(2)

            request1.enqueue(object : Callback<MovieNowPlayingModel> {
                override fun onResponse(
                    call: Call<MovieNowPlayingModel>,
                    response: Response<MovieNowPlayingModel>
                ) {
                    similarMovies1 = response.body()?.results
                    _recommendedMovies1.postValue(response.body())
                    requestCountDownLatch.countDown()
                }

                override fun onFailure(call: Call<MovieNowPlayingModel>, t: Throwable) {
                    errorMessage.postValue(t.message)
                    println(t.message)
                }
            })

            request2.enqueue(object : Callback<MovieNowPlayingModel> {
                override fun onResponse(
                    call: Call<MovieNowPlayingModel>,
                    response: Response<MovieNowPlayingModel>
                ) {
                    similarMovies2 = response.body()?.results
                    requestCountDownLatch.countDown()
                }

                override fun onFailure(call: Call<MovieNowPlayingModel>, t: Throwable) {
                    errorMessage.postValue(t.message)
                    println(t.message)
                }
            })

            requestCountDownLatch.await()

            val combinedSimilarMovies = (similarMovies1 ?: emptyList()) + (similarMovies2 ?: emptyList())

            val sortedList = combinedSimilarMovies.sortedWith(compareByDescending<Result> { it.vote_count }
                .thenByDescending { it.vote_average })


            _similarMovies.postValue(sortedList)

        }

    }

}