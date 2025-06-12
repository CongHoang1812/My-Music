package com.example.musicapp.ui.library.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicapp.data.model.playlist.Playlist

class PlaylistViewModel : ViewModel() {
    private val _playlists = MutableLiveData<List<Playlist>>()
    val playlist: LiveData<List<Playlist>>
        get() = _playlists

    fun setPlaylist(playlists: List<Playlist>) {
        _playlists.value = playlists

    }
}