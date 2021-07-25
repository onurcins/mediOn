package com.onurcinstas.medion.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.onurcinstas.medion.R
import com.onurcinstas.medion.databinding.HomeMeditationItemBinding
import com.onurcinstas.medion.model.MediaItem
import com.onurcinstas.medion.model.home.HomeMeditationItem
import com.onurcinstas.medion.util.getTime
import com.onurcinstas.medion.view.HomeFragmentDirections

class HomeMeditationsAdapter(private val items: ArrayList<HomeMeditationItem>): RecyclerView.Adapter<HomeMeditationsAdapter.ViewHolder>() {

    class ViewHolder(var view: HomeMeditationItemBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val view = DataBindingUtil.inflate<HomeMeditationItemBinding>(inflater,
            R.layout.home_meditation_item,parent,false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.view.homeMeditation = items[position]

        holder.view.meditationLL.setOnClickListener {
            val image = items[position].image.large
            val title = items[position].title
            val content = items[position].content
            val date = items[position].releaseDate

            val media = MediaItem(image, title, content, getTime(date))

            val action = HomeFragmentDirections.actionHomeFragmentToMediaFragment(media)

            Navigation.findNavController(it).navigate(action)
        }
    }

}