package com.example.musicapp.ui.library.playlist

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
import com.example.musicapp.databinding.FragmentPlaylistBinding
import com.example.musicapp.ui.library.detail.PlaylistDetailFragment
import com.example.musicapp.ui.library.detail.PlaylistDetailViewModel
import com.example.musicapp.ui.library.playlist.creation.DialogPlaylistCreationFragment
import com.example.musicapp.ui.library.playlist.more.MorePlaylistFragment
import com.example.musicapp.ui.library.playlist.more.MorePlaylistViewModel
import com.example.musicapp.ui.viewmodel.ShareViewModel

class PlaylistFragment : Fragment() {
    private lateinit var binding: FragmentPlaylistBinding
    private lateinit var adapter: PlaylistAdapter
    private val playlistViewModel: PlaylistViewModel by activityViewModels{
        val application = requireActivity().application as MusicApplication
        PlaylistViewModel.Factory(application.getPlaylistRepository())
    }

    private val morePlaylistViewModel: MorePlaylistViewModel by activityViewModels()
    private val detailPlaylistViewModel: PlaylistDetailViewModel by activityViewModels()
    private var shouldNavigateToDetail = false


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
        playlistViewModel.playlists.observe(viewLifecycleOwner){playlists->
            adapter.updatePlaylist(playlists)
        }
        playlistViewModel.playlists.observe(viewLifecycleOwner) {
            morePlaylistViewModel.setPlaylists(it)
        }
        playlistViewModel.playlistWithSongs.observe(viewLifecycleOwner) { playlistWithSongs ->
            if (shouldNavigateToDetail) {
                playlistWithSongs.playlist?.let {
                    it.updateSongList(playlistWithSongs.songs)
                    ShareViewModel.instance.addPlaylist(it)
                }
                detailPlaylistViewModel.setPlaylistWithSongs(playlistWithSongs)
                navigateToPlaylistDetail()
                shouldNavigateToDetail = false
            }
        }


    }


    private fun setUpView() {
        adapter = PlaylistAdapter(
            object : PlaylistAdapter.OnPlaylistClickListener {
                override fun onPlaylistClick(playlist: Playlist) {
                    ShareViewModel.instance.addPlaylist(playlist)
                    playlistViewModel.getPlaylistWithSongByPlaylistId(playlist.id)
                    shouldNavigateToDetail = true
                }

                override fun onPlaylistMenuOptionClick(playlist: Playlist) {
                    //todo
                }

            }

        )
        binding.includeButtonCreatePlaylist.btnItemCreatePlaylist.setOnClickListener {
            showDialogToCreatePlaylist()
        }
        binding.includeButtonCreatePlaylist.textItemCreatePlaylist.setOnClickListener {
            showDialogToCreatePlaylist()
        }
        binding.btnMorePlaylist.setOnClickListener {
            navigateToMorePlaylist()
        }
        binding.textTitlePlaylist.setOnClickListener {
            navigateToMorePlaylist()
        }
        binding.rvPlaylist.adapter = adapter
    }
    private fun navigateToPlaylistDetail() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, PlaylistDetailFragment::class.java, null)
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToMorePlaylist() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, MorePlaylistFragment::class.java, null)
            .addToBackStack(null)
            .commit()
    }

    private fun showDialogToCreatePlaylist() {
        val listener = object : DialogPlaylistCreationFragment.OnClickListener {
            override fun onClick(playlistName: String) {
                playlistViewModel.createNewPlaylist(playlistName)
            }
        }
        val textChangeListener = object : DialogPlaylistCreationFragment.OnTextChangeListener {
            override fun onTextChange(playlistName: String) {
                playlistViewModel.findPlaylistByName(playlistName)
            }
        }
        val dialog = DialogPlaylistCreationFragment(listener, textChangeListener)
        val tag = DialogPlaylistCreationFragment.TAG
        dialog.show(requireActivity().supportFragmentManager, tag)
    }

}