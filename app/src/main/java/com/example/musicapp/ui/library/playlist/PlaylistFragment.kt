package com.example.musicapp.ui.library.playlist

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.musicapp.R
import com.example.musicapp.data.model.playlist.Playlist
import com.example.musicapp.databinding.FragmentPlaylistBinding

class PlaylistFragment : Fragment() {
    private lateinit var binding: FragmentPlaylistBinding
    private lateinit var adapter: PlaylistAdapter
    private val playlistViewModel: PlaylistViewModel by activityViewModels()

    private val viewModel: PlaylistViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        observerData()
    }

    private fun observerData() {
        playlistViewModel.playlist.observe(viewLifecycleOwner){playlists->
            adapter.updatePlaylist(playlists)
        }
    }

    private fun setUpView() {
        adapter = PlaylistAdapter(
            object : PlaylistAdapter.OnPlaylistClickListener {
                override fun onPlaylistClick(playlist: Playlist) {
                    //todo
                }

                override fun onPlaylistMenuOptionClick(playlist: Playlist) {
                    //todo
                }

            }
        )
        binding.rvPlaylist.adapter = adapter
    }
}