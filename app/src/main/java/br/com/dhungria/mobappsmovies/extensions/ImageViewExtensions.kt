package br.com.dhungria.mobappsmovies.extensions

import android.widget.ImageView
import br.com.dhungria.mobappsmovies.R
import coil.load

fun ImageView.tryLoadImage(
    url: String? = null,
    fallback: Int = R.drawable.image_default
) {
    load(url) {
        fallback(fallback)
        error(R.drawable.image_default)
        placeholder(R.drawable.placeholder)
    }
}