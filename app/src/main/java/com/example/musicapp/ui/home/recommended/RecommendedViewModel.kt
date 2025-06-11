package com.example.musicapp.ui.home.recommended

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicapp.data.model.song.Song
import com.example.musicapp.data.repository.song.SongRepositoryImpl

class RecommendedViewModel : ViewModel() {

    private val _songs = MutableLiveData<List<Song>>()
    val songs: MutableLiveData<List<Song>>
        get() = _songs

    fun setSongs(songs: List<Song>){
        _songs.postValue(songs)

    }


}