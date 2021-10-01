package com.test.movieapp.extension

import android.os.Bundle

/**
 * Intent Extension to get arguments data
 */
fun Bundle.getImageUrl(): String {
    return getString("imgUrl") ?: throw IllegalArgumentException()
}

fun Bundle.getTrackName(): String {
    return getString("trackName") ?: throw IllegalArgumentException()
}

fun Bundle.getGenre(): String {
    return getString("genre") ?: throw IllegalArgumentException()
}

fun Bundle.getPrice(): Double {
    return getDouble("price") ?: throw IllegalArgumentException()
}

fun Bundle.getCurrency(): String {
    return getString("currency") ?: throw IllegalArgumentException()
}

fun Bundle.getDescription(): String {
    return getString("description") ?: throw IllegalArgumentException()
}
