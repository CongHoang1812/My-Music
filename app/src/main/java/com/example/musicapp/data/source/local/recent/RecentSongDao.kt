package com.example.musicapp.data.source.local.recent

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.musicapp.data.model.RecentSong
import com.example.musicapp.data.model.song.Song
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentSongDao {
    @get:Query("SELECT * FROM recent_songs ORDER BY play_at DESC LIMIT 30")
    val recentSongs: Flow<List<Song>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg songs: RecentSong)
}