package com.onurcinstas.medion.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.onurcinstas.medion.R
import com.onurcinstas.medion.databinding.HomeStoryItemBinding
import com.onurcinstas.medion.model.MediaItem
import com.onurcinstas.medion.model.home.HomeStoryItem
import com.onurcinstas.medion.util.getTime
import com.onurcinstas.medion.util.returnSqImageHeight
import com.onurcinstas.medion.view.HomeFragmentDirections

class HomeStoriesAdapter(private val items: ArrayList<HomeStoryItem>): RecyclerView.Adapter<HomeStoriesAdapter.ViewHolder>() {

    class ViewHolder(var view: HomeStoryItemBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<HomeStoryItemBinding>(inflater,
            R.layout.home_story_item,parent,false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.view.homeStory = items[position]

        holder.view.storyItemImage.layoutParams.height = returnSqImageHeight(holder.view.root.context, 66.00f, 2)

        holder.view.storyLL.setOnClickListener {
            val image = items[position].image.large
            val title = items[position].name
            val content = items[position].text
            val date = items[position].date

            val media = MediaItem(image, title, content, getTime(date))

            val action = HomeFragmentDirections.actionHomeFragmentToMediaFragment(media)

            Navigation.findNavController(it).navigate(action)
        }
    }

}