package com.example.musicapp.ui.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.musicapp.data.model.playlist.Playlist
import com.example.musicapp.data.model.song.Song
import com.example.musicapp.data.repository.playlist.PlaylistRepositoryImpl
import com.example.musicapp.data.repository.recent.RecentSongRepositoryImpl
import com.example.musicapp.data.repository.song.SongRepositoryImpl

class LibraryViewModel(
    private val recentSongRepository: RecentSongRepositoryImpl,
    private val songRepository: SongRepositoryImpl,
    private val playListRepository: PlaylistRepositoryImpl
) : ViewModel(){
    val recentSongs: LiveData<List<Song>> = recentSongRepository.recentSong.asLiveData()
    val favoriteSongs: LiveData<List<Song>> = songRepository.favoriteSongs.asLiveData()
    val playlists: LiveData<List<Playlist>> = playListRepository.playlists.asLiveData()


    class Factory(
        private val recentSongRepository: RecentSongRepositoryImpl,
        private val songRepository: SongRepositoryImpl,
        private val playListRepository : PlaylistRepositoryImpl
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LibraryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LibraryViewModel(recentSongRepository, songRepository, playListRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}