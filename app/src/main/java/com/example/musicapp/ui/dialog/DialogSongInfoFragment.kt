package com.example.musicapp.ui.dialog

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.musicapp.R
import com.example.musicapp.data.model.song.Song
import com.example.musicapp.databinding.FragmentDialogSongInfoBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogSongInfoFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentDialogSongInfoBinding
    companion object {
        fun newInstance() = DialogSongInfoFragment()
        const val TAG = "DialogSongInfoFragment"
    }
    private val viewModel: DialogSongInfoViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogSongInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.song.observe(viewLifecycleOwner) {
            showSongInfo(it)
        }
    }

    @SuppressLint("StringFormatInvalid")
    private fun showSongInfo(song: Song){
        val title = getString(R.string.text_song_detail_title, song.title)
        val artist = getString(R.string.text_song_detail_artist, song.artist)

        val album = getString(R.string.text_song_detail_album, song.album)
        val duration = getString(R.string.text_song_detail_duration, song.duration)
        val counter = getString(R.string.text_song_detail_counter, song.counter)
        val replay = getString(R.string.text_song_detail_replay_counter, song.replay)

        val favorite = getString(R.string.text_song_detail_favorite_status,
            if(song.favorite)getString(R.string.yes)else getString(R.string.no))
        val notavailable = getString(R.string.not_available)
        val genre = getString(R.string.text_song_detail_genre, notavailable)
        val year = getString(R.string.text_song_detail_year, notavailable)



        Glide.with(this)
            .load(song.image).error(R.drawable.ic_album)
            .circleCrop().into(binding.imageDetailSongArtwork)
        binding.textSongDetailTitle.text = title
        binding.textSongDetailArtist.text = artist
        binding.textSongDetailAlbum.text = album
        binding.textSongDetailDuration.text = duration
        binding.textSongDetailCounter.text = counter
        binding.textSongDetailReplayCounter.text = replay
        binding.textSongDetailFavoriteStatus.text = favorite
        binding.textSongDetailGenre.text = genre
        binding.textSongDetailYear.text = year
    }
}