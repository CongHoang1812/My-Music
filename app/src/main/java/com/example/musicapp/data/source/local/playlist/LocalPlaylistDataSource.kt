package com.example.musicapp.data.source.local.playlist

import android.database.sqlite.SQLiteConstraintException
import com.example.musicapp.data.model.playlist.Playlist
import com.example.musicapp.data.model.playlist.PlaylistSongCrossRef
import com.example.musicapp.data.model.playlist.PlaylistWithSongs
import com.example.musicapp.data.source.remote.PlaylistDataSource
import kotlinx.coroutines.flow.Flow

class LocalPlaylistDataSource(
    private val playlistDao: PlaylistDao
) :PlaylistDataSource.Local {
    override val playlists: Flow<List<Playlist>>
        get() = playlistDao.playlists
    override val allPlaylists: Flow<List<Playlist>>
        get() = playlistDao.allPlaylists

    override fun getAllPlaylistWithSongs(): Flow<List<PlaylistWithSongs>> {
        return playlistDao.getAllPlaylistWithSongs()
    }

    override fun getAllPlaylistWithSongsByPlaylistId(playlistId: Int): Flow<PlaylistWithSongs> {
        return playlistDao.getAllPlaylistWithSongsByPlaylistId(playlistId)
    }

    override suspend fun insertPlaylistSongCrossRef(obj: PlaylistSongCrossRef) : Long {
        return try{
            playlistDao.insertPlaylistSongCrossRef(obj)
        }catch (e: SQLiteConstraintException){
            -1L
        }
    }

    override suspend fun createPlaylist(playlist: Playlist) {
        playlistDao.insert(playlist)
    }

    override suspend fun updatePlaylist(playlist: Playlist) {
        playlistDao.update(playlist)
    }

    override suspend fun deletePlaylist(playlist: Playlist) {
        playlistDao.delete(playlist)
    }

    override suspend fun findPlaylistByName(name: String): Playlist? {
        return playlistDao.findPlaylistByName(name)
    }


}