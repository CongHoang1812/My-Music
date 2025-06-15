package com.example.musicapp.ui.discovery.foryou


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.musicapp.MusicApplication
import com.example.musicapp.R
import com.example.musicapp.data.model.song.Song
import com.example.musicapp.databinding.FragmentForYouBinding
import com.example.musicapp.ui.PlayerBaseFragment
import com.example.musicapp.ui.detail.DetailFragment
import com.example.musicapp.ui.detail.DetailViewModel
import com.example.musicapp.ui.home.recommended.SongAdapter
import com.example.musicapp.ui.viewmodel.ShareViewModel
import com.example.musicapp.utils.MusicAppUtils

class ForYouFragment : PlayerBaseFragment() {
    private lateinit var binding: FragmentForYouBinding
    private lateinit var songAdapter: SongAdapter
    private val forYouViewModel: ForYouViewModel by activityViewModels {
        val application = requireActivity().application as MusicApplication
        ForYouViewModel.Factory(application.songRepository)
    }
    private val detailViewModel: DetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForYouBinding.inflate(inflater, container, false)
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
                    val playlistName = MusicAppUtils.DefaultPlaylistName.FOR_YOU.value
                    playSong(song, index, playlistName)
                }
            },
            object : SongAdapter.OnSongOptionMenuClickListener {
                override fun onClick(song: Song) {
                    showOptionMenu(song)
                }
            }
        )
        binding.includeForYouSong.rvSongList.adapter = songAdapter
        binding.btnMoreForYou.setOnClickListener {
            navigateToDetailScreen()
        }
        binding.textTitleForYou.setOnClickListener {
            navigateToDetailScreen()
        }
    }

    private fun navigateToDetailScreen() {
        val playlistName = MusicAppUtils.DefaultPlaylistName.FOR_YOU.value
        val screenName = getString(R.string.title_for_you)
        detailViewModel.setScreenName(screenName)
        detailViewModel.setPlaylistName(playlistName)
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, DetailFragment::class.java, null)
            .addToBackStack(null)
            .commit()
    }

    private fun observeData() {
        forYouViewModel.songs.observe(viewLifecycleOwner) { songs ->
            songAdapter.updateSongs(songs)
        }
        forYouViewModel.top40ForYouSongs.observe(viewLifecycleOwner) { songs ->
            detailViewModel.setSong(songs)
            ShareViewModel.instance.setUpPlayList(songs, MusicAppUtils.DefaultPlaylistName.FOR_YOU.value)
        }
    }
}