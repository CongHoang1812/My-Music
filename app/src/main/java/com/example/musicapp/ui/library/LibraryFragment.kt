package com.example.musicapp.ui.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.musicapp.MusicApplication
import com.example.musicapp.databinding.FragmentLibraryBinding
import com.example.musicapp.ui.library.favorite.FavoriteViewModel
import com.example.musicapp.ui.library.playlist.PlaylistViewModel
import com.example.musicapp.ui.library.recent.RecentViewModel

class LibraryFragment: Fragment() {
    private lateinit var binding: FragmentLibraryBinding
    private val libraryViewModel: LibraryViewModel by viewModels {
        val application = requireActivity().application as MusicApplication
        LibraryViewModel.Factory(
            application.getRecentSongRepository(),
            application.songRepository,
            application.getPlaylistRepository()
        )
    }
    private val recentSongViewModel: RecentViewModel by activityViewModels()
    private val favoriteViewModel: FavoriteViewModel by activityViewModels()
    private val playlistViewModel: PlaylistViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        if (savedInstanceState != null) {
            val scrollPosition = savedInstanceState.getInt(SCROLL_POSITION)
            binding.scrollViewLibrary.post {
                binding.scrollViewLibrary.scrollTo(0, scrollPosition)
            }
        }
    }
    private fun observeData() {
        libraryViewModel.recentSongs.observe(viewLifecycleOwner) { recentSongs ->
            recentSongViewModel.setRecentSongs(recentSongs)
        }
        libraryViewModel.favoriteSongs.observe(viewLifecycleOwner){favoriteSongs ->
            favoriteViewModel.setSongs(favoriteSongs)
        }
//        libraryViewModel.playlists.observe(viewLifecycleOwner){playlists ->
//            playlistViewModel.setPlaylist(playlists)
//        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SCROLL_POSITION, binding.scrollViewLibrary.scrollY)
    }
    companion object {
        const val SCROLL_POSITION = "net.braniumacademy.musicapplication.ui.library.SCROLL_POSITION"
    }
}