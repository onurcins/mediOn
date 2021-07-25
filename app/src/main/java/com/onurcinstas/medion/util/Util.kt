package com.onurcinstas.medion.util

import android.content.Context
import android.content.res.Resources
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.onurcinstas.medion.R
import android.text.format.DateFormat
import java.util.*

fun ImageView.downloadFromUrl(url: String?){

    val options = RequestOptions()
        .placeholder(R.drawable.bg_banner)
        .error(R.drawable.bg_banner)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)

}


@BindingAdapter("android:downloadUrl")
fun downloadImage(view: ImageView, url:String?) {
    view.downloadFromUrl(url)
}

fun getTime(time: Long): String {
    val cal = Calendar.getInstance(Locale.ENGLISH)
    cal.timeInMillis = time * 1000

    return DateFormat.format("dd/MM/yyyy, EEE", cal).toString()
}

fun returnSqImageHeight(context: Context, dp: Float, gridCount: Int) : Int{
    val marginPx = dp * context.resources.displayMetrics.density
    val screenPx = Resources.getSystem().displayMetrics.widthPixels

    return ((screenPx - marginPx) / gridCount).toInt()
}