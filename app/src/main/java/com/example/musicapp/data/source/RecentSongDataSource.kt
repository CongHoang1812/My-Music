package com.example.musicapp.data.source

import com.example.musicapp.data.model.RecentSong
import com.example.musicapp.data.model.song.Song
import kotlinx.coroutines.flow.Flow

interface RecentSongDataSource {
    interface Local {
        val recentSong: Flow<List<Song>>
        suspend fun insert(vararg recentSongs: RecentSong)
    }
    interface  Remote{
        //Todo
    }
}