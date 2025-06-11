package com.example.musicapp.data.repository.recent

import com.example.musicapp.data.model.RecentSong
import com.example.musicapp.data.model.song.Song
import kotlinx.coroutines.flow.Flow

interface RecentSongRepository {
    interface Local{
        val recentSong : Flow<List<Song>>
        suspend fun insert(vararg recentSong: RecentSong)
    }
    interface Remote{
        //todo
    }
}