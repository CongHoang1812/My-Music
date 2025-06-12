package com.example.musicapp.data.source.local.playlist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.musicapp.data.model.playlist.Playlist
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {
    @get:Query("SELECT * FROM playlists LIMIT 10")
    val playlists : Flow<List<Playlist>>

    @Insert
    suspend fun insert(playlist: Playlist)

    @Delete
    suspend fun delete(playlist: Playlist)


    @Update
    suspend fun update(playlist: Playlist)

}