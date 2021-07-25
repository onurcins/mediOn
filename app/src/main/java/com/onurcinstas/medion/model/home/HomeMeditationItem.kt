package com.onurcinstas.medion.model.home

data class HomeMeditationItem(
    val title: String,
    val subtitle: String,
    val image: ImageItem,
    val releaseDate: Long,
    val content: String
)
