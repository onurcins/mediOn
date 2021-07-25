package com.onurcinstas.medion.model
import java.io.Serializable

class MediaItem(
    val image: String,
    val title: String,
    val content: String,
    val date: String
) : Serializable