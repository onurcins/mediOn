package com.onurcinstas.medion.view

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.onurcinstas.medion.R
import com.onurcinstas.medion.databinding.FragmentMediaBinding


class MediaFragment : Fragment() {

    private lateinit var binding : FragmentMediaBinding
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.window?.statusBarColor = resources.getColor(
            R.color.detail_nav,
            resources.newTheme()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_media, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = "https://d2r0ihkco3hemf.cloudfront.net/bgxupraW2spUpr_y2.mp3"
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(url)
            prepare() // might take long! (for buffering, etc)
        }


        arguments?.let {

            binding.media = MediaFragmentArgs.fromBundle(it).media
        }

        binding.mediaBackButton.setOnClickListener {
            val action = MediaFragmentDirections.actionMediaFragmentToHomeFragment()

            Navigation.findNavController(it).navigate(action)
        }

        binding.playButtonCons.setOnClickListener {
            isPlaying = !isPlaying
            if (isPlaying) {
                binding.mediaPlayImage.setImageResource(R.drawable.ic_pause)
                mediaPlayer?.start()
            } else {
                binding.mediaPlayImage.setImageResource(R.drawable.ic_play)
                mediaPlayer?.pause()
            }

        }
    }

    override fun onStop() {
        super.onStop()
        binding.mediaPlayImage.setImageResource(R.drawable.ic_play)
        isPlaying = false
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}