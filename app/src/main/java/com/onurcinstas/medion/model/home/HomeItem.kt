package com.onurcinstas.medion.model.home

data class HomeItem(
    val isBannerEnabled: Boolean,
    val meditations: ArrayList<HomeMeditationItem>,
    val stories: ArrayList<HomeStoryItem>
)