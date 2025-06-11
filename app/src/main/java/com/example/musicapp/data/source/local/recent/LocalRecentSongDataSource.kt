package com.example.musicapp.data.source.local.recent

import com.example.musicapp.data.model.RecentSong
import com.example.musicapp.data.model.song.Song
import com.example.musicapp.data.source.RecentSongDataSource
import kotlinx.coroutines.flow.Flow

class LocalRecentSongDataSource(
    private val recentSongDao: RecentSongDao
): RecentSongDataSource.Local  {
    override val recentSong: Flow<List<Song>>
        get() = recentSongDao.recentSongs

    override suspend fun insert(vararg recentSongs: RecentSong) {
        recentSongDao.insert(*recentSongs)
    }

}