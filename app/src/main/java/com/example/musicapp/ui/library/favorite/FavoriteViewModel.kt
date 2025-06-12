package com.example.musicapp.ui.library.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicapp.data.model.song.Song

class FavoriteViewModel : ViewModel() {
    private val _songs = MutableLiveData<List<Song>>()
    val songs: LiveData<List<Song>> = _songs
    fun setSongs(songs: List<Song>) {
        _songs.value = songs

    }
}