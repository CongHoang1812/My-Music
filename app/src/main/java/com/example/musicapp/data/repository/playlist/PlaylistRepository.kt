package com.example.musicapp.data.repository.playlist

import com.example.musicapp.data.model.playlist.Playlist
import com.example.musicapp.data.model.playlist.PlaylistSongCrossRef
import com.example.musicapp.data.model.playlist.PlaylistWithSongs
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    interface Local {
        val playlists: Flow<List<Playlist>>
        val allPlaylists: Flow<List<Playlist>>
        fun getAllPlaylistWithSongs(): Flow<List<PlaylistWithSongs>>
        fun getAllPlaylistWithSongsByPlaylistId(playlistId: Int): Flow<PlaylistWithSongs>
        suspend fun createPlaylist(playlist: Playlist)
        suspend fun updatePlaylist(playlist: Playlist)
        suspend fun createPlaylistSongCrossRef(playlistSongCrossRef: PlaylistSongCrossRef): Long
        suspend fun deletePlaylist(playlist: Playlist)
        suspend fun findPlaylistByName(name: String): Playlist?

    }
    interface Remote{
        //todo
    }
}