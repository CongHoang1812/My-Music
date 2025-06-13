package com.example.musicapp.ui.library.playlist.more

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.musicapp.MusicApplication
import com.example.musicapp.R
import com.example.musicapp.data.model.playlist.Playlist
import com.example.musicapp.databinding.FragmentMorePlaylistBinding
import com.example.musicapp.ui.library.detail.PlaylistDetailFragment
import com.example.musicapp.ui.library.detail.PlaylistDetailViewModel
import com.example.musicapp.ui.library.playlist.PlaylistAdapter
import com.example.musicapp.ui.library.playlist.PlaylistViewModel
import com.example.musicapp.ui.viewmodel.ShareViewModel

class MorePlaylistFragment : Fragment() {
    private lateinit var binding: FragmentMorePlaylistBinding
    private lateinit var adapter: PlaylistAdapter
    private val morePlaylistViewModel: MorePlaylistViewModel by activityViewModels()
    private val playlistDetailViewModel: PlaylistDetailViewModel by activityViewModels()
    private val playlistViewModel: PlaylistViewModel by activityViewModels {
        val application = requireActivity().application as MusicApplication
        PlaylistViewModel.Factory(application.getPlaylistRepository())
    }
    private var isNavigateToPlaylistDetail = false



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMorePlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeData()
    }
    private fun setupView() {
        binding.toolbarMorePlaylist.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        adapter = PlaylistAdapter(
            object : PlaylistAdapter.OnPlaylistClickListener {
                override fun onPlaylistClick(playlist: Playlist) {
                    playlistViewModel.getPlaylistWithSongByPlaylistId(playlist.id)
                    isNavigateToPlaylistDetail = true
                }

                override fun onPlaylistMenuOptionClick(playlist: Playlist) {
                    // todo
                }
            }
        )
        binding.rvMorePlaylist.adapter = adapter
    }

    private fun observeData() {
        morePlaylistViewModel.playlists.observe(viewLifecycleOwner) { playlists ->
            adapter.updatePlaylist(playlists)
        }
        playlistViewModel.playlistWithSongs.observe(viewLifecycleOwner) { playlistWithSongs ->
            if (isNavigateToPlaylistDetail) {
                playlistWithSongs.playlist?.let {
                    it.updateSongList(playlistWithSongs.songs)
                    ShareViewModel.instance.addPlaylist(it)
                }
                playlistDetailViewModel.setPlaylistWithSongs(playlistWithSongs)
                navigateToPlaylistDetail()
                isNavigateToPlaylistDetail = false
            }
        }
    }
    private fun navigateToPlaylistDetail() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, PlaylistDetailFragment::class.java, null)
            .addToBackStack(null)
            .commit()
    }
}