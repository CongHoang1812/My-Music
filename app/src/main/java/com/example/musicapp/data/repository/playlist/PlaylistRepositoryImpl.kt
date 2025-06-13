package com.example.musicapp.data.repository.playlist

import com.example.musicapp.data.model.playlist.Playlist
import com.example.musicapp.data.model.playlist.PlaylistSongCrossRef
import com.example.musicapp.data.model.playlist.PlaylistWithSongs
import com.example.musicapp.data.source.remote.PlaylistDataSource
import kotlinx.coroutines.flow.Flow

class PlaylistRepositoryImpl(
    private val localDataSource: PlaylistDataSource.Local
) : PlaylistRepository.Local, PlaylistRepository.Remote {
    override val playlists: Flow<List<Playlist>>
        get() = localDataSource.playlists
    override val allPlaylists: Flow<List<Playlist>>
        get() = localDataSource.allPlaylists

    override fun getAllPlaylistWithSongs(): Flow<List<PlaylistWithSongs>> {
        return localDataSource.getAllPlaylistWithSongs()
    }

    override fun getAllPlaylistWithSongsByPlaylistId(playlistId: Int): Flow<PlaylistWithSongs> {
        return localDataSource.getAllPlaylistWithSongsByPlaylistId(playlistId)
    }

    override suspend fun createPlaylist(playlist: Playlist) {
        localDataSource.createPlaylist(playlist)
    }

    override suspend fun updatePlaylist(playlist: Playlist) {
        localDataSource.updatePlaylist(playlist)
    }

    override suspend fun createPlaylistSongCrossRef(playlistSongCrossRef: PlaylistSongCrossRef) : Long{
        return localDataSource.insertPlaylistSongCrossRef(playlistSongCrossRef)
    }

    override suspend fun deletePlaylist(playlist: Playlist) {
        localDataSource.deletePlaylist(playlist)

    }

    override suspend fun findPlaylistByName(name: String): Playlist? {
        return localDataSource.findPlaylistByName(name)
    }
}