package br.com.dhungria.mobappsmovies.data.models


data class MovieNowPlayingModel(
    val dates: Dates,
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)