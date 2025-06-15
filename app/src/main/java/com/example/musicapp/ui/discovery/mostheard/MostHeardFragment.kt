package com.example.musicapp.ui.discovery.mostheard

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.musicapp.MusicApplication
import com.example.musicapp.R
import com.example.musicapp.data.model.song.Song
import com.example.musicapp.databinding.FragmentMostHeardBinding
import com.example.musicapp.ui.PlayerBaseFragment
import com.example.musicapp.ui.detail.DetailFragment
import com.example.musicapp.ui.detail.DetailViewModel
import com.example.musicapp.ui.home.recommended.SongAdapter
import com.example.musicapp.ui.viewmodel.ShareViewModel
import com.example.musicapp.utils.MusicAppUtils

class MostHeardFragment : PlayerBaseFragment() {
    private lateinit var binding: FragmentMostHeardBinding
    private lateinit var songAdapter: SongAdapter
    private val mostHeardViewModel: MostHeardViewModel by activityViewModels {
        val application = requireActivity().application as MusicApplication
        MostHeardViewModel.Factory(application.songRepository)
    }
    private val detailViewModel: DetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMostHeardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeData()
    }

    private fun setupView() {
        songAdapter = SongAdapter(
            object : SongAdapter.OnSongClickListener {
                override fun onClick(song: Song, index: Int) {
                    val playlistName = MusicAppUtils.DefaultPlaylistName.MOST_HEARD.value
                    playSong(song, index, playlistName)
                }
            },
            object : SongAdapter.OnSongOptionMenuClickListener {
                override fun onClick(song: Song) {
                    showOptionMenu(song)
                }
            }
        )
        binding.includeMostHeardSong.rvSongList.adapter = songAdapter
        binding.btnMoreMostHeard.setOnClickListener {
            navigateToDetailScreen()
        }
        binding.textTitleMostHeard.setOnClickListener {
            navigateToDetailScreen()
        }
    }

    private fun navigateToDetailScreen() {
        val playlistName = MusicAppUtils.DefaultPlaylistName.MOST_HEARD.value
        val screenName = getString(R.string.title_most_heard)
        detailViewModel.setScreenName(screenName)
        detailViewModel.setPlaylistName(playlistName)
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, DetailFragment::class.java, null)
            .addToBackStack(null)
            .commit()
    }

    private fun observeData() {
        mostHeardViewModel.songs.observe(viewLifecycleOwner) { songs ->
            songAdapter.updateSongs(songs)
        }
        mostHeardViewModel.top40MostHeardSongs.observe(viewLifecycleOwner) { songs ->
            detailViewModel.setSong(songs)
            ShareViewModel.instance.setUpPlayList(songs, MusicAppUtils.DefaultPlaylistName.MOST_HEARD.value)
        }
    }
}