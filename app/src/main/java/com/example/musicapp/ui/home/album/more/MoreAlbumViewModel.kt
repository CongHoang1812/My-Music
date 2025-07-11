package com.example.musicapp.ui.home.album.more

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicapp.data.model.album.Album

class MoreAlbumViewModel : ViewModel() {
    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>> = _albums
    fun setAlbums(albums: List<Album>) {
        _albums.value = albums

    }
}