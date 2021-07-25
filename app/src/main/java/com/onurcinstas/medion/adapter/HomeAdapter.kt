package com.onurcinstas.medion.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.onurcinstas.medion.R
import com.onurcinstas.medion.databinding.HomeBannerItemBinding
import com.onurcinstas.medion.databinding.HomeMeditationsRvBinding
import com.onurcinstas.medion.databinding.HomeStoriesRvBinding
import com.onurcinstas.medion.databinding.HomeTitleItemBinding
import com.onurcinstas.medion.model.home.*


class HomeAdapter(
    val context: Context,
    private val items: ArrayList<Any>
)
    : RecyclerView.Adapter<HomeAdapter.BaseViewHolder<*>>() {


    companion object {

        private const val TYPE_TITLE = 0

        private const val TYPE_MEDITATION = 1

        private const val TYPE_BANNER = 2

        private const val TYPE_STORY= 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {

        return when (viewType) {
            TYPE_TITLE -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.home_title_item, parent, false)
                TitleViewHolder(view)
            }
            TYPE_MEDITATION -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.home_meditations_rv, parent, false)
                MeditationViewHolder(view)
            }
            TYPE_BANNER -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.home_banner_item, parent, false)
                BannerViewHolder(view)
            }
            TYPE_STORY -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.home_stories_rv, parent, false)
                StoryViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element =
            items[position]
        when (holder) {
            is TitleViewHolder -> holder.bind(element as HomeTitleItem)
            is MeditationViewHolder -> holder.bind(element as HomeMeditationsRVItem)
            is BannerViewHolder -> holder.bind(element as HomeBannerItem)
            is StoryViewHolder -> holder.bind(element as HomeStoriesRVItem)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemViewType(position: Int): Int {

        return when (items[position]) {

            is HomeTitleItem -> TYPE_TITLE
            is HomeMeditationsRVItem -> TYPE_MEDITATION
            is HomeBannerItem -> TYPE_BANNER
            is HomeStoriesRVItem-> TYPE_STORY
            else
            -> throw IllegalArgumentException("Invalid type of data $position")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    inner class TitleViewHolder(itemView: View) : BaseViewHolder<HomeTitleItem>(
        itemView
    ) {
        private val binding = HomeTitleItemBinding.bind(itemView)

        override fun bind(item: HomeTitleItem) {
            binding.homeTitle = item
        }

    }

    inner class MeditationViewHolder(itemView: View) : BaseViewHolder<HomeMeditationsRVItem>(itemView) {

        private val binding = HomeMeditationsRvBinding.bind(itemView)

        override fun bind(item: HomeMeditationsRVItem) {
            binding.meditationsRecyclerView.adapter = HomeMeditationsAdapter(item.arr)
            binding.meditationsRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }

    }

    inner class BannerViewHolder(itemView: View) : BaseViewHolder<HomeBannerItem>(itemView) {

        private val binding = HomeBannerItemBinding.bind(itemView)

        override fun bind(item: HomeBannerItem) {
            binding.homeBanner = item
        }

    }

    inner class StoryViewHolder(itemView: View) : BaseViewHolder<HomeStoriesRVItem>(itemView) {

        private val binding = HomeStoriesRvBinding.bind(itemView)

        override fun bind(item: HomeStoriesRVItem) {
            binding.storiesRecyclerView.adapter = HomeStoriesAdapter(item.arr)
            binding.storiesRecyclerView.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        }

    }

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

        abstract fun bind(item: T)
    }

    fun updateList(newList: List<Any>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

}