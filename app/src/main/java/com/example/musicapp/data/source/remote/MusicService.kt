package com.example.musicapp.data.source.remote

import com.example.musicapp.data.model.album.AlbumList
import com.example.musicapp.data.model.song.SongList
import retrofit2.Response
import retrofit2.http.GET

interface MusicService {
    @GET("/resources/braniumapis/songs.json")
    suspend fun loadSongs(): Response<SongList>
    @GET("/resources/braniumapis/playlist.json")
    suspend fun loadAlbums(): Response<AlbumList>

}