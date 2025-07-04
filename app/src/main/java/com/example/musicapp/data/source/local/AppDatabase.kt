package com.example.musicapp.data.source.local

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import com.example.musicapp.data.model.RecentSong
import com.example.musicapp.data.model.album.Album
import com.example.musicapp.data.model.artist.Artist
import com.example.musicapp.data.model.artist.ArtistSongCrossRef
import com.example.musicapp.data.model.playlist.Playlist
import com.example.musicapp.data.model.playlist.PlaylistSongCrossRef
import com.example.musicapp.data.model.song.Song
import com.example.musicapp.data.source.local.artist.ArtistDao
import com.example.musicapp.data.source.local.playlist.PlaylistDao
import com.example.musicapp.data.source.local.recent.RecentSongDao
import com.example.musicapp.data.source.local.song.SongDao

@Database(entities = [
    Song::class,
    Album::class,
    Playlist::class,
    RecentSong::class,
    PlaylistSongCrossRef::class,
    Artist::class,
      ArtistSongCrossRef::class



                     ],
    version = 6,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 5, to = 6, spec = AppDatabase.MyAutoMigration::class)
    ])
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun songDao(): SongDao
    abstract fun albumDao(): AlbumDao
    abstract fun playlistDao(): PlaylistDao
    abstract fun recentSongDao(): RecentSongDao
    abstract fun artistDao(): ArtistDao
    @RenameColumn(tableName = "artists", fromColumnName = "id", toColumnName = "artist_id")
    internal class MyAutoMigration : AutoMigrationSpec

    companion object{
        @Volatile
        private var _instance: AppDatabase? = null
        fun getInstance(context: Context):AppDatabase {
            if (_instance == null) {
                synchronized(AppDatabase::class.java) {
                    if (_instance == null) {
                        _instance = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "music.db"
                        ).build()
                    }
                }
            }
            return _instance!!
        }
    }

}