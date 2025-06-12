package com.example.musicapp.ui.library.recent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicapp.data.model.song.Song

class RecentViewModel : ViewModel() {
    private val _recentSongs = MutableLiveData<List<Song>>()
    val recentSongs: LiveData<List<Song>> = _recentSongs

    fun setRecentSongs(song: List<Song>){
        _recentSongs.value = song
    }
}