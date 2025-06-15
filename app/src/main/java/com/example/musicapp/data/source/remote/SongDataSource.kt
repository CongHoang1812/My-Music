package com.example.musicapp.data.source.remote

import com.example.musicapp.ResultCallback
import com.example.musicapp.data.model.song.Song
import com.example.musicapp.data.model.song.SongList
import kotlinx.coroutines.flow.Flow

interface SongDataSource {
    interface Local{
        val songs: List<Song>

        val favoriteSongs: Flow<List<Song>>

        val top15MostHeardSongs: Flow<List<Song>>

        val top40MostHeardSongs: Flow<List<Song>>

        val top15ForYouSongs: Flow<List<Song>>

        val top40ForYouSongs: Flow<List<Song>>
    }

    interface Remote{
        suspend fun loadSongs(callback: ResultCallback<com.example.musicapp.data.source.Result<SongList>>)
    }
}