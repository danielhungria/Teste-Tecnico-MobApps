package br.com.dhungria.mobappsmovies.data.models

data class MovieTopRatedModel(
    val page: Int,
    val results: List<ResultTopRated>,
    val total_pages: Int,
    val total_results: Int
)