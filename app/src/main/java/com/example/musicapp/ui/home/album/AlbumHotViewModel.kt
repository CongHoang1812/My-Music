package com.example.musicapp.ui.home.album

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.ResultCallback
import com.example.musicapp.data.model.album.Album
import com.example.musicapp.data.repository.AlbumRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.musicapp.data.source.Result
class AlbumHotViewModel : ViewModel() {
    private val albumRepository = AlbumRepositoryImpl()
    private val _albums = MutableLiveData<List<Album>>()
    val albums : LiveData<List<Album>> = _albums

    fun setAlbums(albums: List<Album>){
        _albums.postValue(albums)

    }

}