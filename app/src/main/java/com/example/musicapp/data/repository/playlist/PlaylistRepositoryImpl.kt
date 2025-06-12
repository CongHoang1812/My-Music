package com.example.musicapp.data.repository.playlist

import com.example.musicapp.data.model.playlist.Playlist
import com.example.musicapp.data.source.remote.PlaylistDataSource
import kotlinx.coroutines.flow.Flow

class PlaylistRepositoryImpl(
    private val localDataSource: PlaylistDataSource.Local
) : PlaylistRepository.Local, PlaylistRepository.Remote {
    override val playlists: Flow<List<Playlist>>
        get() = localDataSource.playlists

    override suspend fun createPlaylist(playlist: Playlist) {
        localDataSource.createPlaylist(playlist)
    }

    override suspend fun updatePlaylist(playlist: Playlist) {
        localDataSource.updatePlaylist(playlist)
    }

    override suspend fun deletePlaylist(playlist: Playlist) {
        localDataSource.deletePlaylist(playlist)

    }
}