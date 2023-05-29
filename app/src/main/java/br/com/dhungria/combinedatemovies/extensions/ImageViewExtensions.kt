package br.com.dhungria.combinedatemovies.extensions

import android.widget.ImageView
import br.com.dhungria.combinedatemovies.R
import coil.load

fun ImageView.tryLoadImage(
    url: String? = "",
    fallback: Int = R.drawable.image_default
) {
    load(url) {
        fallback(fallback)
        error(R.drawable.image_default)
        placeholder(R.drawable.placeholder)
    }
}