package com.onurcinstas.medion.model
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
class MediaItem(
    val image: String,
    val title: String,
    val content: String,
    val date: String
) : Parcelable