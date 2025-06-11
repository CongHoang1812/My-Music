package com.example.musicapp.data.source.local.song

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.musicapp.data.model.song.Song
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDao {
    @get: Query("SELECT * FROM songs")
    val songs: List<Song>

    @get:Query("SELECT * FROM songs WHERE favorite = 1")
    val favoriteSongs: Flow<List<Song>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg songs: Song)

    @Delete
    suspend fun delete(song: Song)

    @Update
    suspend fun update(song: Song)

    @Query("UPDATE songs SET favorite = :favorite WHERE song_id = :id")
    suspend fun updateFavorite(id: String, favorite: Boolean)

    @Query("SELECT * FROM songs WHERE song_id = :id")
    suspend fun getSongById(id: String): Song?



}