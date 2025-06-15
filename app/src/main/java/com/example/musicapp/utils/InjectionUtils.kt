package com.example.musicapp.utils

import android.content.Context
import com.example.musicapp.data.repository.artist.ArtistRepositoryImpl
import com.example.musicapp.data.repository.playlist.PlaylistRepositoryImpl
import com.example.musicapp.data.repository.recent.RecentSongRepositoryImpl
import com.example.musicapp.data.repository.song.SongRepositoryImpl
import com.example.musicapp.data.source.SongDataSource
import com.example.musicapp.data.source.local.AppDatabase
import com.example.musicapp.data.source.local.artist.LocalArtistDataSource
import com.example.musicapp.data.source.local.playlist.LocalPlaylistDataSource
import com.example.musicapp.data.source.local.recent.LocalRecentSongDataSource
import com.example.musicapp.data.source.local.song.LocalSongDataSource
import com.example.musicapp.data.source.remote.ArtistDataSource
import com.example.musicapp.data.source.remote.PlaylistDataSource

object InjectionUtils {
    fun provideRecentSongDataSource(context: Context) : LocalRecentSongDataSource{
        val database = AppDatabase.getInstance(context)
        return LocalRecentSongDataSource(database.recentSongDao())
    }
    fun provideRecentSongRepository(dataSource: LocalRecentSongDataSource) : RecentSongRepositoryImpl{
        return RecentSongRepositoryImpl(dataSource)

    }
    fun provideSongDataSource(context: Context) : SongDataSource.Local{
        val database = AppDatabase.getInstance(context)
        return LocalSongDataSource(database.songDao())
    }

    fun provideSongRepository(
        dataSource: SongDataSource.Local
    ): SongRepositoryImpl {
        return SongRepositoryImpl(dataSource)
    }
    fun providePlaylistDataSource(context: Context): PlaylistDataSource.Local {
        val database = AppDatabase.getInstance(context)
        return LocalPlaylistDataSource(database.playlistDao())

    }
    fun providePlaylistRepository(
        dataSource: PlaylistDataSource.Local
    ): PlaylistRepositoryImpl {
        return PlaylistRepositoryImpl(dataSource)
    }
    fun provideArtistDataSource(context: Context): ArtistDataSource.Local{
        val database = AppDatabase.getInstance(context)
        return LocalArtistDataSource(database.artistDao())
    }
    fun provideArtistRepository(dataSource: ArtistDataSource.Local): ArtistRepositoryImpl {
        return ArtistRepositoryImpl(dataSource)
    }
}