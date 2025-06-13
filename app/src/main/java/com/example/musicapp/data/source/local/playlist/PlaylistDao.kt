package com.example.musicapp.data.source.local.playlist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.musicapp.data.model.playlist.Playlist
import com.example.musicapp.data.model.playlist.PlaylistSongCrossRef
import com.example.musicapp.data.model.playlist.PlaylistWithSongs
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {
    @get:Query("SELECT * FROM playlists LIMIT 10")
    val playlists : Flow<List<Playlist>>

    @Query("SELECT * FROM playlists WHERE name = :name")
    suspend fun findPlaylistByName(name: String): Playlist?



    @get:Query("SELECT * FROM playlists")
    val allPlaylists: Flow<List<Playlist>>

    @Insert
    suspend fun insert(playlist: Playlist)

    @Delete
    suspend fun delete(playlist: Playlist)


    @Update
    suspend fun update(playlist: Playlist)

    @Transaction
    @Query("SELECT * FROM playlists")
    fun getAllPlaylistWithSongs(): Flow<List<PlaylistWithSongs>>


    @Transaction
    @Query("SELECT * FROM playlists WHERE playlist_id = :playlistId")
    fun getAllPlaylistWithSongsByPlaylistId(playlistId: Int): Flow<PlaylistWithSongs>

    @Insert
    suspend fun insertPlaylistSongCrossRef(obj: PlaylistSongCrossRef) : Long

}
