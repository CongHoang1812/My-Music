package com.example.musicapp.ui.library.detail

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
import com.example.musicapp.databinding.FragmentPlaylistDetailBinding
import com.example.musicapp.ui.PlayerBaseFragment
import com.example.musicapp.ui.home.recommended.SongAdapter
import com.example.musicapp.utils.MusicAppUtils

class PlaylistDetailFragment : PlayerBaseFragment
    () {
    private lateinit var binding: FragmentPlaylistDetailBinding
    private lateinit var adapter: SongAdapter
    private val playlistDetailViewModel: PlaylistDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaylistDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeData()
    }
    private fun setupView() {
        binding.includePlaylistDetail
            .toolbarPlaylistDetails
            .setNavigationOnClickListener {
                requireActivity().supportFragmentManager
                    .popBackStack()
            }
        val title = getString(R.string.title_playlist_detail)
        binding.includePlaylistDetail
            .textPlaylistDetailTitle.text = title
        adapter = SongAdapter(
            object : SongAdapter.OnSongClickListener {
                override fun onClick(song: Song, index: Int) {
                    val playlistName =
                        playlistDetailViewModel.playlistWithSongs.value?.playlist?.name
                            ?: MusicAppUtils.DefaultPlaylistName.DEFAULT.value
                    playSong(song, index, playlistName)
                }
            },
            object : SongAdapter.OnSongOptionMenuClickListener {
                override fun onClick(song: Song) {
                    showOptionMenu(song)
                }
            }
        )
        binding.includePlaylistDetail
            .includeSongList
            .rvSongList.adapter = adapter
    }

    private fun observeData() {
        playlistDetailViewModel.playlistWithSongs
            .observe(viewLifecycleOwner) { playlistWithSongs ->
                adapter.updateSongs(playlistWithSongs.songs)
                binding.includePlaylistDetail
                    .textPlaylistDetailTitle.text = playlistWithSongs.playlist?.name
                val numberOfSong =
                    getString(R.string.text_playlist_detail_num_of_song, playlistWithSongs.songs.size)
                binding.includePlaylistDetail
                    .textPlaylistDetailNumOfSong.text = numberOfSong
                val artworkId = playlistWithSongs.songs.firstOrNull()?.image
                Glide.with(this)
                    .load(artworkId)
                    .error(R.drawable.ic_album)
                    .into(binding.includePlaylistDetail.imagePlaylistArtwork)
            }
    }
}