package com.example.musicapp.data.repository.recent

import com.example.musicapp.data.model.RecentSong
import com.example.musicapp.data.model.song.Song
import com.example.musicapp.data.source.local.recent.LocalRecentSongDataSource
import kotlinx.coroutines.flow.Flow

class RecentSongRepositoryImpl(
    private val localDataSource: LocalRecentSongDataSource
) : RecentSongRepository.Local, RecentSongRepository.Remote {
    override val recentSong: Flow<List<Song>>
        get() = localDataSource.recentSong

    override suspend fun insert(vararg recentSong: RecentSong) {
        localDataSource.insert(*recentSong)
    }


}