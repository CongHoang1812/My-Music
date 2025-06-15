package com.example.musicapp.data.repository.song

import com.example.musicapp.ResultCallback
import com.example.musicapp.data.model.song.Song
import com.example.musicapp.data.model.song.SongList
import com.example.musicapp.data.source.Result
import kotlinx.coroutines.flow.Flow

interface SongRepository {
    interface Local{
        val songs: List<Song>
        val top15MostHeardSongs: Flow<List<Song>>

        val top40MostHeardSongs: Flow<List<Song>>

        val favoriteSongs: Flow<List<Song>>

        val top15ForYouSongs: Flow<List<Song>>

        val top40ForYouSongs: Flow<List<Song>>

        suspend fun getSongById(id: String): Song?

        suspend fun insert(vararg songs: Song)

        suspend fun delete(song: Song)

        suspend fun update(song: Song)

        suspend fun updateFavorite(id: String, favorite: Boolean)


    }
    interface Remote{
        suspend fun loadSongs(callback: ResultCallback<Result<SongList>>)
    }
}