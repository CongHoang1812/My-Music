package com.example.musicapp.ui.home.album.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicapp.data.model.album.Album
import com.example.musicapp.data.model.playlist.Playlist
import com.example.musicapp.data.model.song.Song

class DetailAlbumViewModel : ViewModel() {
    private val _album = MutableLiveData<Album>()
    private val _songs = MutableLiveData<List<Song>>()
    private val _playlist = MutableLiveData<Playlist>()
    val album : LiveData<Album>
        get() = _album
    val songs : LiveData<List<Song>>
        get() = _songs

    fun setAlbum(album: Album){
        _album.value = album

    }
    val playlist: LiveData<Playlist>
        get() = _playlist

    fun extractSongs(album: Album, songs: List<Song>?){
       songs?.let {
           val currentPlaylist = Playlist(name=album.name)
           currentPlaylist.id = -1
           val songsList = mutableListOf<Song>()
           for(songId in album.songs){
               val songIndex = songs.indexOfFirst { it.id == songId }
               if(songIndex != -1){
                   songsList.add(songs[songIndex])
               }
           }
           _songs.value = songsList
           currentPlaylist.updateSongList(songsList)
           _playlist.value = currentPlaylist
       }

    }
}