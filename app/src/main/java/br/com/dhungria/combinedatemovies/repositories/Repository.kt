package br.com.dhungria.combinedatemovies.repositories

import br.com.dhungria.combinedatemovies.data.models.SupportModel
import br.com.dhungria.combinedatemovies.data.retrofit.RetrofitService
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class Repository @Inject constructor(private val retrofitService: RetrofitService) {

    private val firebaseFireStore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getMoviesNowPlaying(page: Int) = retrofitService.getMoviesNowPlaying(page)

    fun getDetailMovie(id: Int) = retrofitService.getDetailMovie(id)

    fun getTopRatedMovie() = retrofitService.getTopRatedMovie()

    fun searchMovie(queryMovie: String) = retrofitService.searchMovie(queryMovie)

    fun getMovieRecommendations(id: Int, page: Int) = retrofitService.getMovieRecommendations(id, page)

    fun querySupportService(): Task<QuerySnapshot> {
        return firebaseFireStore
            .collection("supportService")
            .get()
    }

    fun uploadSupport(supportModel: SupportModel, uuid: String){
        Firebase.firestore
            .collection("support_combine_date")
            .document(uuid)
            .set(supportModel)
    }

}