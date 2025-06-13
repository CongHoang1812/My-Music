package com.example.musicapp.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicapp.data.model.song.Song

class DetailViewModel : ViewModel() {
    private val _songs = MutableLiveData<List<Song>>()
    private val _playlistName = MutableLiveData<String>()
    private val _screenName = MutableLiveData<String>()
    val songs: MutableLiveData<List<Song>>
        get() = _songs
    val playlistName: MutableLiveData<String>
        get() = _playlistName
    val screenName: MutableLiveData<String>
        get() = _screenName

    fun setSong(song: List<Song>) {
        _songs.value = song
    }

    fun setPlaylistName(name: String) {
        _playlistName.value = name
    }

    fun setScreenName(name: String) {
        _screenName.value = name
    }


}