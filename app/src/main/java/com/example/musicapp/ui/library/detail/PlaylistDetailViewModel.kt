package com.example.musicapp.ui.library.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicapp.data.model.playlist.PlaylistWithSongs

class PlaylistDetailViewModel : ViewModel() {
    private val _playlistWithSongs: MutableLiveData<PlaylistWithSongs>
        get() = MutableLiveData<PlaylistWithSongs>()
    val playlistWithSongs: LiveData<PlaylistWithSongs> = _playlistWithSongs

    fun setPlaylistWithSongs(playlistWithSongs: PlaylistWithSongs) {
        _playlistWithSongs.value = playlistWithSongs
    }
}