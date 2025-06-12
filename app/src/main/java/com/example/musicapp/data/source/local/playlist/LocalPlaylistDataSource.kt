package com.example.musicapp.data.source.local.playlist

import com.example.musicapp.data.model.playlist.Playlist
import com.example.musicapp.data.source.remote.PlaylistDataSource
import kotlinx.coroutines.flow.Flow

class LocalPlaylistDataSource(
    private val playlistDao: PlaylistDao
) :PlaylistDataSource.Local {
    override val playlists: Flow<List<Playlist>>
        get() = playlistDao.playlists

    override suspend fun createPlaylist(playlist: Playlist) {
        playlistDao.insert(playlist)
    }

    override suspend fun updatePlaylist(playlist: Playlist) {
        playlistDao.update(playlist)
    }

    override suspend fun deletePlaylist(playlist: Playlist) {
        playlistDao.delete(playlist)
    }




}