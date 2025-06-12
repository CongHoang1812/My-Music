package com.example.musicapp.data.repository.playlist

import com.example.musicapp.data.model.playlist.Playlist
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    interface Local {
        val playlists: Flow<List<Playlist>>
        suspend fun createPlaylist(playlist: Playlist)
        suspend fun updatePlaylist(playlist: Playlist)
        suspend fun deletePlaylist(playlist: Playlist)

    }
    interface Remote{
        //todo
    }
}